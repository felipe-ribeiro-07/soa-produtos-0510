package br.gov.cps.sp.soa_produtos.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "consultarProdutoResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"nome", "descricao", "marca", "estoque"})
public class ConsultarProdutoResponse {

    private String nome;
    private String descricao;
    private String marca;
    private int estoque;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public int getEstoque() { return estoque; }
    public void setEstoque(int estoque) { this.estoque = estoque; }
}