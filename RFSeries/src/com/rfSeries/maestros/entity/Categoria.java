package com.rfSeries.maestros.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

import com.rfBaseCore.entity.BaseEntity;
import com.rfBaseCore.security.maestros.entity.Usuario;

@Entity
@Table(name = "categorias")
public class Categoria extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4860780928728664483L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "codigo")
	private Integer codigo;
	@Column(name = "descri")
	private String descri;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuarioCreacion", nullable = true)
	private Usuario usuarioCreacion;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuarioModificacion", nullable = true)
	private Usuario usuarioModificacion;
	@Column(name = "fechaCreacion")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fechaCreacion;
	@Column(name = "fechaModificacion")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fechaModificacion;

	public Integer getId() {
		return id;
	}

	public Categoria() {
	}

	public Categoria(Integer codigo) {
		this.codigo = codigo;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescri() {
		return descri;
	}

	public void setDescri(String descri) {
		this.descri = descri;
	}

	public Usuario getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(Usuario usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public Usuario getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(Usuario usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
}
