package br.com.svo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListaCargo implements Serializable {

    public static final long serialVersionUID = 1L;

    private List<Cargo> cargos = new ArrayList<>();

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }
}
