package com.rfBaseCore.filtro;

import java.util.List;

import javax.persistence.criteria.JoinType;

import com.rfBaseCore.constants.EnumTipoFiltro;

public class BaseFiltro {
	private EnumTipoFiltro tipoFiltro;
	private String campo;
	private String campoJoin;
	private Object value;
	private Object value2;
	private String alias;
	private EnumTipoFiltro tipoFiltro2;
	private List<BaseFiltro> filtros;
	private boolean tienenSegundaCondicion;
	private JoinType join;
	
	public BaseFiltro(){
		
	}

	public EnumTipoFiltro getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(EnumTipoFiltro tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getCampoJoin() {
		return campoJoin;
	}

	public void setCampoJoin(String campoJoin) {
		this.campoJoin = campoJoin;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getValue2() {
		return value2;
	}

	public void setValue2(Object value2) {
		this.value2 = value2;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public EnumTipoFiltro getTipoFiltro2() {
		return tipoFiltro2;
	}

	public void setTipoFiltro2(EnumTipoFiltro tipoFiltro2) {
		this.tipoFiltro2 = tipoFiltro2;
	}

	public List<BaseFiltro> getFiltros() {
		return filtros;
	}

	public void setFiltros(List<BaseFiltro> filtros) {
		this.filtros = filtros;
	}

	public boolean isTienenSegundaCondicion() {
		return tienenSegundaCondicion;
	}

	public void setTienenSegundaCondicion(boolean tienenSegundaCondicion) {
		this.tienenSegundaCondicion = tienenSegundaCondicion;
	}

	public JoinType getJoin() {
		return join;
	}

	public void setJoin(JoinType join) {
		this.join = join;
	}
	
}
