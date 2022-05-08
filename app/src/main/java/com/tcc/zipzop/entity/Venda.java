package com.tcc.zipzop.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity
public class Venda {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private Float valor_pago;
    private Float valor_venda;
    private LocalDateTime data_pagamento;
    private Integer forma_pagamento_id; //FK
    private Integer caixa_id; //FK

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getValor_pago() {
        return valor_pago;
    }

    public void setValor_pago(Float valor_pago) {
        this.valor_pago = valor_pago;
    }

    public Float getValor_venda() {
        return valor_venda;
    }

    public void setValor_venda(Float valor_venda) {
        this.valor_venda = valor_venda;
    }

    public LocalDateTime getData_pagamento() {
        return data_pagamento;
    }

    public void setData_pagamento(LocalDateTime data_pagamento) {
        this.data_pagamento = data_pagamento;
    }

    public Integer getForma_pagamento_id() {
        return forma_pagamento_id;
    }

    public void setForma_pagamento_id(Integer forma_pagamento_id) {
        this.forma_pagamento_id = forma_pagamento_id;
    }

    public Integer getCaixa_id() {
        return caixa_id;
    }

    public void setCaixa_id(Integer caixa_id) {
        this.caixa_id = caixa_id;
    }
}
