package com.tcc.zipzop.entity;

import java.time.LocalDateTime;

public class Venda {

    private Long id;
    private Float valor_pago;
    private Float valor_venda;
    private LocalDateTime data_pagamento;
    private Integer forma_pagamento_id; //FK
    private Integer caixa_id; //FK

}
