package com.tcc.zipzop.entity;

import java.time.LocalDateTime;

public class Caixa {

    private Long id;
    private Float fundo;
    private LocalDateTime data_abertura;
    private LocalDateTime data_fechamento;
    private Integer estoque_id; //FK

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getFundo() {
        return fundo;
    }

    public void setFundo(Float fundo) {
        this.fundo = fundo;
    }

    public LocalDateTime getData_abertura() {
        return data_abertura;
    }

    public void setData_abertura(LocalDateTime data_abertura) {
        this.data_abertura = data_abertura;
    }

    public LocalDateTime getData_fechamento() {
        return data_fechamento;
    }

    public void setData_fechamento(LocalDateTime data_fechamento) {
        this.data_fechamento = data_fechamento;
    }

    public Integer getEstoque_id() {
        return estoque_id;
    }

    public void setEstoque_id(Integer estoque_id) {
        this.estoque_id = estoque_id;
    }
}
