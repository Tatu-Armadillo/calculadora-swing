package br.com.calculadora.models;

import java.util.ArrayList;
import java.util.List;

public class Memoria {

    private enum TipoComando {
        ZERAR, SINAL, NUMERO, DIV, MULT, SUB, SOMA, IGUAL, VIRGULA;
    };

    private static final Memoria instancia = new Memoria();
    private final List<MemoriaObservador> observadores = new ArrayList<>();

    private TipoComando ultimoOperacao = null;
    private boolean substituir = false;
    private String textoAutal = "";
    private String textoBuffer = "";

    private Memoria() {

    }

    public static Memoria getInstancia() {
        return instancia;
    }

    public void adicionarObservador(MemoriaObservador observador) {
        observadores.add(observador);
    }

    public void processarCOmando(String texto) {
        TipoComando tipoComando = detectarTipoCOmando(texto);
        if (tipoComando == null) {
            return;
        } else if (tipoComando == TipoComando.ZERAR) {
            textoAutal = "";
            textoBuffer = "";
            substituir = false;
            ultimoOperacao = null;
        } else if (tipoComando == TipoComando.SINAL && textoAutal.contains("-")) {
            textoAutal = textoAutal.substring(1);
        } else if (tipoComando == TipoComando.SINAL && !textoAutal.contains("-")) {
            textoAutal = "-" + textoAutal;
        } else if (tipoComando == TipoComando.NUMERO || tipoComando == TipoComando.VIRGULA) {
            textoAutal = substituir ? texto : textoAutal + texto;
            substituir = false;
        } else {
            substituir = true;
            textoAutal = obterResultadoOpercao();
            textoBuffer = textoAutal;
            ultimoOperacao = tipoComando;
        }

        observadores.forEach(o -> o.valorAlterado(getTextoAutal()));
    }

    private String obterResultadoOpercao() {
        if (ultimoOperacao == null || ultimoOperacao == TipoComando.IGUAL) {
            return textoAutal;
        }

        double numeroBuffer = Double.parseDouble(textoBuffer.replace(",", "."));
        double numeroAtual = Double.parseDouble(textoAutal.replace(",", "."));
        double resultado = 0;

        if (ultimoOperacao == TipoComando.SOMA) {
            resultado = numeroBuffer + numeroAtual;
        } else if (ultimoOperacao == TipoComando.SUB) {
            resultado = numeroBuffer - numeroAtual;
        } else if (ultimoOperacao == TipoComando.MULT) {
            resultado = numeroBuffer * numeroAtual;
        } else if (ultimoOperacao == TipoComando.DIV) {
            resultado = numeroBuffer / numeroAtual;
        }

        String resultadoString = Double.toString(resultado).replace(".", ",");
        boolean inteiro = resultadoString.endsWith(",0");
        return inteiro ? resultadoString.replace(",0", "") : resultadoString;
    }

    private TipoComando detectarTipoCOmando(String texto) {
        if (textoAutal.isEmpty() && texto.equals("0")) {
            return null;
        }
        try {
            Integer.parseInt(texto);
            return TipoComando.NUMERO;
        } catch (Exception e) {
            if ("AC".equals(texto)) {
                return TipoComando.ZERAR;
            } else if ("÷".equals(texto)) {
                return TipoComando.DIV;
            } else if ("*".equals(texto)) {
                return TipoComando.MULT;
            } else if ("+".equals(texto)) {
                return TipoComando.SOMA;
            } else if ("-".equals(texto)) {
                return TipoComando.SUB;
            } else if ("=".equals(texto)) {
                return TipoComando.IGUAL;
            } else if ("±".equals(texto)) {
                return TipoComando.SINAL;
            } else if (",".equals(texto) && !textoAutal.contains(",")) {
                return TipoComando.VIRGULA;
            }
        }
        return null;
    }

    // #region Getters and Setters
    public String getTextoAutal() {
        return textoAutal.isEmpty() ? "0" : textoAutal;
    }

    public void setTextoAutal(String textoAutal) {
        this.textoAutal = textoAutal;
    }

    public String getTextoBuffer() {
        return textoBuffer;
    }

    public void setTextoBuffer(String textoBuffer) {
        this.textoBuffer = textoBuffer;
    }
    // #endregion
}
