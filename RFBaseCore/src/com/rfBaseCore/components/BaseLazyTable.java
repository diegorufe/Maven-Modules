package com.rfBaseCore.components;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import com.rfBaseCore.filtro.BaseFiltro;
import com.rfBaseCore.service.BaseService;

public class BaseLazyTable<T> extends LazyDataModel<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7006454865639798311L;

	private List<T> datasource;
	private BaseService<T> service;
	private boolean lazy;
	private boolean paginator;
	private boolean reflow;
	private T selection;
	private int numColums;
	private int numReg;
	private int rowsPage;
	private String header;
	private List<BaseDataTableColumns> columns;
	private int firstRequest;
	private int pageSizeRequest;
	private List<SortMeta> multiSortMetaRequest;
	private LinkedList<BaseFiltro> filtros;
	private DataTable datatable;
	private boolean multiViewState;

	public BaseLazyTable() {
		this.datasource = new ArrayList<T>();
		lazy = true;
		paginator = true;
		reflow = true;
		rowsPage = 15;
		columns = new ArrayList<>();
		datatable = new DataTable();
	}

	@Override
	public Object getRowKey(T object) {
		return object.toString();
	}

	@Override
	public T getRowData(String rowKey) {
		for (T object : datasource) {
			if (object.toString().equals(rowKey))
				return object;
		}
		return null;
	}

	@Override
	public List<T> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
		datasource.clear();
		LinkedHashMap<String, SortOrder> orders = new LinkedHashMap<>();
		LinkedList<SortMeta> multiSortMetaLinked = null;
		if (multiSortMeta != null && multiSortMeta.size() > 0) {
			multiSortMetaLinked = new LinkedList<SortMeta>(multiSortMeta);
		} else if (multiSortMeta == null || multiSortMeta.size() == 0) {
			if (multiSortMetaRequest != null && multiSortMetaRequest.size() > 0) {
				multiSortMetaLinked = new LinkedList<SortMeta>(multiSortMetaRequest);
			}
		}
		int[] iniFin = new int[2];
		iniFin[0] = first;
		iniFin[1] = rowsPage;
		if (multiSortMetaLinked != null && multiSortMetaLinked.size() > 0) {
			for (SortMeta sortMeta : multiSortMetaLinked) {
				if (sortMeta.getSortField() != null && sortMeta.getSortOrder() != null
						&& !sortMeta.getSortOrder().equals(SortOrder.UNSORTED)) {
					orders.put(sortMeta.getSortField(), sortMeta.getSortOrder());
				}
			}
		}
		numReg = getService().countByQuery(orders,filtros);
		datasource = getService().findByQuery(orders,filtros, iniFin);
		setRowCount(numReg);
		if (datasource == null) {
			datasource = new ArrayList<T>();
		}
		this.firstRequest = first;
		this.pageSizeRequest = pageSize;
		this.multiSortMetaRequest = multiSortMeta;
		return datasource;
	}

	public void generaColumsn(String[] columns, String[] sorters, String[] headers) {

		for (int i = 0; i < columns.length; i++) {
			BaseDataTableColumns column = new BaseDataTableColumns(headers[i], columns[i], sorters[i]);
			this.columns.add(column);
		}
	}

	public void reload() {
		load(firstRequest, pageSizeRequest, multiSortMetaRequest, null);
	}

	// Getters and Setters
	public List<T> getDatasource() {
		return datasource;
	}

	public void setDatasource(List<T> datasource) {
		this.datasource = datasource;
	}

	public BaseService<T> getService() {
		return service;
	}

	public void setService(BaseService<T> service) {
		this.service = service;
	}

	public boolean isLazy() {
		return lazy;
	}

	public void setLazy(boolean lazy) {
		this.lazy = lazy;
	}

	public boolean isPaginator() {
		return paginator;
	}

	public void setPaginator(boolean paginator) {
		this.paginator = paginator;
	}

	public boolean isReflow() {
		return reflow;
	}

	public void setReflow(boolean reflow) {
		this.reflow = reflow;
	}

	public T getSelection() {
		return selection;
	}

	public void setSelection(T selection) {
		this.selection = selection;
	}

	public int getNumColums() {
		return numColums;
	}

	public void setNumColums(int numColums) {
		this.numColums = numColums;
	}

	public List<BaseDataTableColumns> getColumns() {
		return columns;
	}

	public int getNumReg() {
		return numReg;
	}

	public void setNumReg(int numReg) {
		this.numReg = numReg;
	}

	public int getRowsPage() {
		return rowsPage;
	}

	public void setRowsPage(int rowsPage) {
		this.rowsPage = rowsPage;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public int getFirstRequest() {
		return firstRequest;
	}

	public void setFirstRequest(int firstRequest) {
		this.firstRequest = firstRequest;
	}

	public int getPageSizeRequest() {
		return pageSizeRequest;
	}

	public void setPageSizeRequest(int pageSizeRequest) {
		this.pageSizeRequest = pageSizeRequest;
	}

	public List<SortMeta> getMultiSortMetaRequest() {
		return multiSortMetaRequest;
	}

	public void setMultiSortMetaRequest(List<SortMeta> multiSortMetaRequest) {
		this.multiSortMetaRequest = multiSortMetaRequest;
	}

	public void setColumns(List<BaseDataTableColumns> columns) {
		this.columns = columns;
	}
	
	public LinkedList<BaseFiltro> getFiltros() {
		return filtros;
	}
	
	public void setFiltros(LinkedList<BaseFiltro> filtros) {
		this.filtros = filtros;
	}
	
	public DataTable getDatatable() {
		return datatable;
	}
	
	public void setDatatable(DataTable datatable) {
		this.datatable = datatable;
	}
	
	public boolean isMultiViewState() {
		return multiViewState;
	}
	
	public void setMultiViewState(boolean multiViewState) {
		this.multiViewState = multiViewState;
	}
}
