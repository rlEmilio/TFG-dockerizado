package com.tfg.koeshiru.DTOS;

import java.io.Serializable;
import java.util.List;

// Bloqueado hasta implementación de tabla intermedia
public class TrabajoDTO implements Serializable {

        private List<Long> trabajosIds;

        public List<Long> getTrabajosIds() {
            return trabajosIds;
        }

        public void setTrabajosIds(List<Long> trabajosIds) {
            this.trabajosIds = trabajosIds;
        }
}
