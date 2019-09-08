package com.demo.consumer.service.Impl;

import com.demo.consumer.dto.util.ExceptionMessage;
import com.demo.consumer.service.SequenceService;
import com.demo.consumer.constant.*;
import com.demo.consumer.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Service
@Slf4j
public class SequenceServiceImpl implements SequenceService {

    @Override
    public Mono<ServerResponse> mutant(ServerRequest request) {
        log.info("[mutant]" + Constante.LOG_INI);

        return request.bodyToMono(RequestDnaDto.class).flatMap(mapper -> {

            if (mapper == null)
                return ServerResponse.badRequest().build();
            try {
                log.info("[mutant][RequestDnaDto: {}]", mapper.toString());

                if (mapper.dna.length > 0) {
                    char[][] matriz = crearMatriz(mapper.dna);
                    if (matriz == null) {
                        throw new ExceptionMessage(ErrorDto.builder().code("DM001").msg("Matriz not equals").build());
                    }
                    if (!this.validarAdnMatriz(matriz)) {
                        throw new ExceptionMessage(ErrorDto.builder().code("DM002").msg("Matriz ADN not validate").build());
                    }
                    if (!this.isMutant(matriz)) {
                        throw new ExceptionMessage(ErrorDto.builder().code("DM003").msg("Is not a Mutant").build());
                    }
                    log.info("[mutant][validacion: {}]", true);
                }
                Mono<ServerResponse> retorno = ServerResponse.status(HttpStatus.CREATED).body(Mono.just(true), boolean.class);
                log.info("[mutant]" + Constante.LOG_OK);
                return retorno;
            } catch (ExceptionMessage ex) {

                log.error("[mutant]" + Constante.LOG_EX, ex.getErrorDto());

                return ServerResponse
                        .status(HttpStatus.BAD_REQUEST)
                        .body(Mono.just(ErrorDto.builder().msg(ex.getErrorDto().getMsg()).code(String.valueOf(ex.getErrorDto().getCode())).build()), ErrorDto.class);
            } catch (Exception ex) {

                log.error("[mutant]" + Constante.LOG_EX, ex);

                return ServerResponse
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Mono.just(ErrorDto.builder().msg(ex.getMessage()).code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())).build()), ErrorDto.class);
            }
        });
    }

    private char[][] crearMatriz(String[] matrizInbound) {
        int x = matrizInbound.length;
        char[] limiteCadena = matrizInbound[0].toCharArray();
        int j = limiteCadena.length;
        if (x != j)
            return null;
        char[][] retorno = new char[x][j];
        //Recorro objeto para objener matriz x*j
        for (int c = 0; c < matrizInbound.length; c++) {
            char[] validaLargo = matrizInbound[c].toCharArray();
            if (validaLargo.length != j) {
                return null;
            }
            retorno[c] = validaLargo;
        }
        return retorno;
    }

    private boolean validarAdnMatriz(char[][] matrizInbound) {
        int x = matrizInbound.length;
        int j = matrizInbound[0].length;
        char[] cadenaValida = Constante.CONTENIDO;
        //Recorro objeto para validar caracteres de matriz x*j
        for (int n = 0; n < x; n++) {
            for (int m = 0; m < j; m++) {
                boolean valida = false;
                for (int c = 0; c < cadenaValida.length; c++) {
                    if (matrizInbound[n][m] == cadenaValida[c]) {
                        valida = true;
                        break;
                    }
                }
                if (!valida) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isMutant(char[][] matrizInbound) {
        int x = matrizInbound.length;
        int adn = 4;
        int mutante = 0;
        char cadenaValidaO = matrizInbound[0][0];
        int contO = 0;
        for (int n = 0; n < x; n++) {
            int i = 0;
            int contV = 0;
            int contH = 0;
            char cadenaValidaV = matrizInbound[i][n];
            char cadenaValidaH = matrizInbound[n][i];
            //Validcion H y V
            while (i < x) {
                log.info("[matrizInboundV: {}]", cadenaValidaV);
                if (cadenaValidaV == matrizInbound[i][n]) {
                    contV = contV + 1;
                } else {
                    cadenaValidaV = matrizInbound[i][n];
                    contV = 1;
                }
                log.info("[matrizInboundH: {}]", cadenaValidaH);
                if (cadenaValidaH == matrizInbound[n][i]) {
                    contH = contH + 1;
                } else {
                    cadenaValidaH = matrizInbound[n][i];
                    contH = 1;
                }
                if (contV == adn) {
                    mutante = mutante + 1;
                }
                if (contH == adn) {
                    mutante = mutante + 1;
                }
                i = i + 1;
            }
            //Validcion O
            log.info("[matrizInboundO: {}]", cadenaValidaO);
            if (cadenaValidaO == matrizInbound[n][n]) {
                contO = contO + 1;
            } else {
                cadenaValidaO = matrizInbound[n][n];
                contO = 1;
            }
            if (contO == 4) {
                mutante = mutante + 1;
            }
            log.info("[Iteracion: {}]", n);
        }
        if (mutante > 1)
            return true;
        return false;
    }

    private void grabarMatriz(char[] adnMutant) {
        log.info("[grabarMatriz: {}]", adnMutant);

    }

    @Override
    public Mono<ServerResponse> stats(ServerRequest request) {
        Mono<ServerResponse> retorno = ServerResponse.status(HttpStatus.CREATED).build();
        return retorno;
    }

    @Override
    public Mono<ServerResponse> health(ServerRequest request) {
        Mono<ServerResponse> retorno = ServerResponse.status(HttpStatus.CREATED).build();
        return retorno;
    }
}
