package com.example.imoveistemporada;

import com.google.gson.annotations.SerializedName;

class CepResponse {
    @SerializedName("cep")
    private String cep;

    @SerializedName("localidade")
    private String cidade;

    // Adicione outros campos, se necessário

    public String getCep() {
        return cep;
    }

    public String getCidade() {
        return cidade;
    }
}

class Usuario
{
    private String uid;  // UID único do usuário
    private String email;

    // Construtores, getters e setters

    public Usuario(String uid, String email)
    {
        this.uid = uid;
        this.email = email;
    }
    public Usuario()
    {
    }
    public void setUid(String uid)
    {
        this.uid = uid;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getUid()
    {
        return uid;
    }

    public String getEmail()
    {
        return email;
    }
}

public class Imovel
{
    private String idImovel;
    private String nomeProprietario;
    private String telefoneContato;
    private String cep;
    private String cidade;
    private double valorDiaria;
    private Usuario proprietario;

    // Construtor
    public Imovel()
    {

    }

    public Imovel(String nomeProprietario, String telefoneContato, String cep, String cidade, double valorDiaria)
    {
        this.nomeProprietario = nomeProprietario;
        this.telefoneContato = telefoneContato;
        this.cep = cep;
        this.cidade = cidade;
        this.valorDiaria = valorDiaria;
    }

    public Imovel(String idImovel,String nomeProprietario, String telefoneContato, String cep, String cidade, double valorDiaria)
    {
        this.idImovel = idImovel;
        this.nomeProprietario = nomeProprietario;
        this.telefoneContato = telefoneContato;
        this.cep = cep;
        this.cidade = cidade;
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
    public String getCidade()
    {
        return cidade;
    }
    public void setCidade(String cidade)
    {
        this.cidade = cidade;
    }
    public double getValorDiaria()
    {
        return valorDiaria;
    }
    public void setValorDiaria(double valorDiaria)
    {
        this.valorDiaria = valorDiaria;
    }
    public Usuario getProprietario()
    {
        return proprietario;
    }
    public void setProprietario(Usuario proprietario)
    {
        this.proprietario = proprietario;
    }
}

