package br.com.alura.TabelaFIPE.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties (ignoreUnknown = true)
public record Modelos (@JsonAlias("modelos") List<Dados> modelos,
					   @JsonAlias("anos") List<Dados> anos){

}
