package com.tcc.zipzop.entity;

import java.time.LocalDateTime;

public class Estoque {

    private Long id;
    private LocalDateTime data_alteracao; //UQ

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getData_alteracao() {
        return data_alteracao;
    }

    public void setData_alteracao(LocalDateTime data_alteracao) {
        this.data_alteracao = data_alteracao;
    }
}
