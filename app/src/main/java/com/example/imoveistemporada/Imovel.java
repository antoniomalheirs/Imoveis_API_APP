package com.example.imoveistemporada;

public class Imovel
{
    private String idImovel;
    private String nomeProprietario;
    private String telefoneContato;
    private String cep;
    private String dadosComplementaresEndereco;
    private double valorDiaria;

    // Construtor
    public Imovel()
    {

    }

    public Imovel(String nomeProprietario, String telefoneContato, String cep, String dadosComplementaresEndereco, double valorDiaria)
    {
        this.idImovel = idImovel;
        this.nomeProprietario = nomeProprietario;
        this.telefoneContato = telefoneContato;
        this.cep = cep;
        this.dadosComplementaresEndereco = dadosComplementaresEndereco;
        this.valorDiaria = valorDiaria;
    }

    public Imovel(String idImovel,String nomeProprietario, String telefoneContato, String cep, String dadosComplementaresEndereco, double valorDiaria)
    {
        this.idImovel = idImovel;
        this.nomeProprietario = nomeProprietario;
        this.telefoneContato = telefoneContato;
        this.cep = cep;
        this.dadosComplementaresEndereco = dadosComplementaresEndereco;
        this.valorDiaria = valorDiaria;
    }

    // Métodos getters e setters (pode ser gerado automaticamente pela IDE)
    public String getIdImovel()
    {
        return idImovel;
    }

    public void setIdImovel(String idImovel)
    {
        this.idImovel = idImovel;
    }

    public String getNomeProprietario()
    {
        return nomeProprietario;
    }

    public void setNomeProprietario(String nomeProprietario)
    {
        this.nomeProprietario = nomeProprietario;
    }

    public String getTelefoneContato()
    {
        return telefoneContato;
    }

    public void setTelefoneContato(String telefoneContato)
    {
        this.telefoneContato = telefoneContato;
    }

    public String getCep()
    {
        return cep;
    }

    public void setCep(String cep)
    {
        this.cep = cep;
    }

    public String getDadosComplementaresEndereco()
    {
        return dadosComplementaresEndereco;
    }

    public void setDadosComplementaresEndereco(String dadosComplementaresEndereco)
    {
        this.dadosComplementaresEndereco = dadosComplementaresEndereco;
    }

    public double getValorDiaria()
    {
        return valorDiaria;
    }

    public void setValorDiaria(double valorDiaria)
    {
        this.valorDiaria = valorDiaria;
    }
}

