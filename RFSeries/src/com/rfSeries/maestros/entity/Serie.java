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
@Table(name = "series")
public class Serie extends BaseEntity {
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoriaId", nullable = true)
	private Categoria categoria;
	@Column(name = "capitulo")
	private Integer capitulo;
	@Column(name = "capitulos")
	private Integer capitulos;
	@Column(name = "temporada")
	private Integer temporada;
	@Column(name = "temporadas")
	private Integer temporadas;
	@Column(name = "activa")
	private boolean activa;
	@Column(name = "repeticion")
	private boolean repeticion;
	@Column(name = "finalizada")
	private boolean finalizada;
	@Column(name = "observa")
	private String observa;
	@Column(name = "vista")
	private boolean vista;

	public Integer getId() {
		return id;
	}

	public Serie() {
	}

	public Serie(Integer codigo) {
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Integer getCapitulo() {
		return capitulo;
	}

	public void setCapitulo(Integer capitulo) {
		this.capitulo = capitulo;
	}

	public Integer getCapitulos() {
		return capitulos;
	}

	public void setCapitulos(Integer capitulos) {
		this.capitulos = capitulos;
	}

	public Integer getTemporada() {
		return temporada;
	}

	public void setTemporada(Integer temporada) {
		this.temporada = temporada;
	}

	public Integer getTemporadas() {
		return temporadas;
	}

	public void setTemporadas(Integer temporadas) {
		this.temporadas = temporadas;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public boolean isRepeticion() {
		return repeticion;
	}

	public void setRepeticion(boolean repeticion) {
		this.repeticion = repeticion;
	}

	public boolean isFinalizada() {
		return finalizada;
	}

	public void setFinalizada(boolean finalizada) {
		this.finalizada = finalizada;
	}

	public String getObserva() {
		return observa;
	}

	public void setObserva(String observa) {
		this.observa = observa;
	}
	public boolean isVista() {
		return vista;
	}
	public void setVista(boolean vista) {
		this.vista = vista;
	}
}
