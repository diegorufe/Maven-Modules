package com.rfBaseCore.components;

import org.primefaces.context.RequestContext;

import com.rfBaseCore.constants.EnumEstadosAbm;

public class BaseAbm<T> {
	private BaseBrowser<T> browser;
	private EnumEstadosAbm estado;
	private String idAbmUpdate;

	public BaseAbm() {
		estado = EnumEstadosAbm.SHOW;
	}

	public BaseAbm(BaseBrowser<T> browser) {
		this.browser = browser;
		this.estado = EnumEstadosAbm.SHOW;
	}

	public BaseBrowser<T> getBrowser() {
		return browser;
	}

	public void setBrowser(BaseBrowser<T> browser) {
		this.browser = browser;
	}

	public EnumEstadosAbm getEstado() {
		return this.estado;
	}

	public void setEstado(EnumEstadosAbm estado) {
		this.estado = estado;
	}

	public String getIdAbmUpdate() {
		return idAbmUpdate;
	}

	public void setIdAbmUpdate(String idAbmUpdate) {
		this.idAbmUpdate = idAbmUpdate;
	}

	public String goShow() {
		if (this.browser.getLazyTable().getSelection() != null) {
			cambiaEstado(EnumEstadosAbm.SHOW);
			this.browser.getController().setEntity(
					this.browser.getController().getService().getByModel(this.browser.getLazyTable().getSelection()));
			this.browser.getController().update();
			RequestContext.getCurrentInstance().execute("PF('"
					+ browser.getController().getClasegenerica().getSimpleName().toLowerCase().trim() + "Abm').show()");
			if (idAbmUpdate == null) {
				RequestContext.getCurrentInstance()
						.update(browser.getController().getClasegenerica().getSimpleName().trim() + "Modal:"
								+ this.getClassName() + "abm");
			} else {
				RequestContext.getCurrentInstance().update(idAbmUpdate);
			}
			this.browser.getController().actionsJoinsTables();
		} else {
			this.browser.getController().getSessionController().getIdiomaController()
					.addMessageInfo("genericos.seleccionaRegistro");
		}
		return null;
	}

	public String goAdd() {
		cambiaEstado(EnumEstadosAbm.ADD);
		RequestContext.getCurrentInstance().execute("PF('"
				+ browser.getController().getClasegenerica().getSimpleName().toLowerCase().trim() + "Abm').show()");
		if (idAbmUpdate == null) {
			RequestContext.getCurrentInstance().update(browser.getController().getClasegenerica().getSimpleName().trim()
					+ "Modal:" + getClassName() + "abm");
		} else {
			RequestContext.getCurrentInstance().update(idAbmUpdate);
		}
		this.browser.getController().actionsJoinsTables();
		return null;
	}

	public String goMod() {
		if (this.browser.getLazyTable().getSelection() != null) {
			cambiaEstado(EnumEstadosAbm.MOD);
			this.browser.getController().setEntity(
					this.browser.getController().getService().getByModel(this.browser.getLazyTable().getSelection()));
			this.browser.getController().update();
			RequestContext.getCurrentInstance().execute("PF('"
					+ browser.getController().getClasegenerica().getSimpleName().toLowerCase().trim() + "Abm').show()");
			if (idAbmUpdate == null) {
				RequestContext.getCurrentInstance()
						.update(browser.getController().getClasegenerica().getSimpleName().trim() + "Modal:"
								+ getClassName() + "abm");
			} else {
				RequestContext.getCurrentInstance().update(idAbmUpdate);
			}
			this.browser.getController().actionsJoinsTables();
		} else {
			this.browser.getController().getSessionController().getIdiomaController()
					.addMessageInfo("genericos.seleccionaRegistro");
		}
		return null;
	}

	public String goDel() {
		if (this.browser.getLazyTable().getSelection() != null) {
			RequestContext.getCurrentInstance().execute("PF('" + getClassName().trim() + "borrarList').show()");
		} else {
			this.browser.getController().getSessionController().getIdiomaController()
					.addMessageInfo("genericos.seleccionaRegistro");
		}
		this.browser.getController().actionsJoinsTables();
		return null;
	}

	public boolean isAdd() {
		return estado == EnumEstadosAbm.ADD;
	}

	public boolean isDel() {
		return estado == EnumEstadosAbm.DEL;
	}

	public boolean isShow() {
		return estado == EnumEstadosAbm.SHOW;
	}

	public boolean isModi() {
		return estado == EnumEstadosAbm.MOD;
	}

	public String add() {
		cambiaEstado(EnumEstadosAbm.SHOW);
		this.browser.getController().save();
		this.browser.getController().setEntity(
				this.browser.getController().getService().getByModel(this.browser.getController().getEntity()));
		this.browser.getController().update();
		this.browser.getController().getSessionController().getIdiomaController()
				.addMessageInfo("genericos.registroAdd");
		if (idAbmUpdate == null) {
			RequestContext.getCurrentInstance().update(browser.getController().getClasegenerica().getSimpleName().trim()
					+ "Modal:" + getClassName() + "abm");
		} else {
			RequestContext.getCurrentInstance().update(idAbmUpdate);
		}
		return null;
	}

	public String modi() {
		cambiaEstado(EnumEstadosAbm.SHOW);
		this.browser.getController().update();
		if (idAbmUpdate == null) {
			RequestContext.getCurrentInstance().update(browser.getController().getClasegenerica().getSimpleName().trim()
					+ "Modal:" + getClassName() + "abm");
		} else {
			RequestContext.getCurrentInstance().update(idAbmUpdate);
		}
		this.browser.getController().getSessionController().getIdiomaController()
				.addMessageInfo("genericos.registroMod");
		return null;
	}

	public String del() {
		this.browser.getController().setEntity(this.browser.getLazyTable().getSelection());
		this.browser.getController().update();
		this.browser.getController().getService().delete(this.browser.getController().getEntity());
		this.browser.getController().newInstance();
		this.browser.getController().getSessionController().getIdiomaController()
				.addMessageInfo("genericos.registroDel");
		return null;
	}

	public String delModal() {
		cambiaEstado(EnumEstadosAbm.SHOW);
		this.browser.getController().getService().delete(this.browser.getController().getEntity());
		this.browser.getController().newInstance();
		this.close();
		this.browser.getController().getSessionController().getIdiomaController()
				.addMessageInfo("genericos.registroDel");
		RequestContext.getCurrentInstance()
				.update(browser.getController().getClasegenerica().getSimpleName().trim() + "Modal");
		return null;
	}

	public String getClassName() {
		return browser.getController().getClasegenerica().getSimpleName().trim();
	}

	public String goShowModal() {
		cambiaEstado(EnumEstadosAbm.SHOW);
		this.browser.getController().setEntity(
				this.browser.getController().getService().getByModel(this.browser.getController().getEntity()));
		if (idAbmUpdate == null) {
			RequestContext.getCurrentInstance().update(browser.getController().getClasegenerica().getSimpleName().trim()
					+ "Modal:" + getClassName() + "abm");
		} else {
			RequestContext.getCurrentInstance().update(idAbmUpdate);
		}
		return null;
	}

	public String goModModal() {
		cambiaEstado(EnumEstadosAbm.MOD);
		if (idAbmUpdate == null) {
			RequestContext.getCurrentInstance().update(browser.getController().getClasegenerica().getSimpleName().trim()
					+ "Modal:" + getClassName() + "abm");
		} else {
			RequestContext.getCurrentInstance().update(idAbmUpdate);
		}
		return null;
	}

	public String close() {
		RequestContext.getCurrentInstance().execute("PF('"
				+ browser.getController().getClasegenerica().getSimpleName().toLowerCase().trim() + "Abm').hide()");
		if (idAbmUpdate == null) {
			RequestContext.getCurrentInstance().update(browser.getController().getClasegenerica().getSimpleName().trim()
					+ "Modal:" + getClassName() + "abm");
		} else {
			RequestContext.getCurrentInstance().update(idAbmUpdate);
		}
		return null;
	}

	private void cambiaEstado(EnumEstadosAbm estado) {
		switch (estado) {
		case ADD:
			this.browser.getController().newInstanceEntityCodigo();
			if (this.browser.getController().getEntity() == null) {
				this.browser.getController().newInstanceEntity();
			}
			this.estado = estado;
			break;
		case MOD:
			this.estado = estado;
			break;
		case DEL:
			this.estado = estado;
			break;
		case SHOW:
			this.estado = estado;
			break;
		default:
			break;
		}
	}

}
