package com.example.model;

public enum BankMethod {
    PIX("PIX"),
    CREDITCARD("CARTÃO DE CREDITO"),
    DEBITCARD("CARTÃO DE DEBITO");

    private String descricao;

    private BankMethod(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}


