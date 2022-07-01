package com.tcc.zipzop.view;

import com.tcc.zipzop.entity.Produto;

import java.util.Date;
import java.util.List;

public class CaixaView {

  //TODO:(passar objetos nas views talvez seja melhor do que os campos, ou fazer heranca)

  private Integer id;
  private Date dataAbertura;
  private Date dataFechamento;
  private Integer caixaFundoValor;
  private Date caixaFundoDataAlteracao;
  private List<Produto> estoqueProdutos;
  private List<CaixaProdutoView> caixaProdutoViews;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Date getDataAbertura() {
    return dataAbertura;
  }

  public void setDataAbertura(Date dataAbertura) {
    this.dataAbertura = dataAbertura;
  }

  public Date getDataFechamento() {
    return dataFechamento;
  }

  public void setDataFechamento(Date dataFechamento) {
    this.dataFechamento = dataFechamento;
  }

  public Integer getCaixaFundoValor() {
    return caixaFundoValor;
  }

  public void setCaixaFundoValor(Integer caixaFundoValor) {
    this.caixaFundoValor = caixaFundoValor;
  }

  public Date getCaixaFundoDataAlteracao() {
    return caixaFundoDataAlteracao;
  }

  public void setCaixaFundoDataAlteracao(Date caixaFundoDataAlteracao) {
    this.caixaFundoDataAlteracao = caixaFundoDataAlteracao;
  }

  public List<Produto> getEstoqueProdutos() {
    return estoqueProdutos;
  }

  public void setEstoqueProdutos(List<Produto> estoqueProdutos) {
    this.estoqueProdutos = estoqueProdutos;
  }

  public List<CaixaProdutoView> getCaixaProdutoViews() {
    return caixaProdutoViews;
  }

  public void setCaixaProdutoViews(List<CaixaProdutoView> caixaProdutoViews) {
    this.caixaProdutoViews = caixaProdutoViews;
  }
}
