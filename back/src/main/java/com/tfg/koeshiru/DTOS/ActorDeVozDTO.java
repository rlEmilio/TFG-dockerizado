package com.tfg.koeshiru.DTOS;

import java.io.Serializable;
import java.util.List;

public class ActorDeVozDTO implements Serializable {

    private List<Long> actoresDeVoz;

    public List<Long> getActoresDeVoz() {
        return actoresDeVoz;
    }

    public void setActoresDeVoz(List<Long> actoresDeVoz) {
        this.actoresDeVoz = actoresDeVoz;
    }

}
