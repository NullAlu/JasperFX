/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author marco
 */
@Entity
@Table(name="productos")
public class Carta implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
   
    @Column(name="precio")
    private double precio;
    @Column(name="tipo")
    private String tipo;
 
   
    @OneToMany(mappedBy="producto",fetch=FetchType.EAGER)
    private List<Pedido> pedidos;
    public Carta() {
    }

    public Carta(Long id, String nombre, double precio,String tipo) {
        this.id = id;
   
        this.precio = precio;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public void setId(Long id) {
        this.id = id;
    }

 
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Carta{" + "id=" + id + ", nombre=" + pedidos + ", precio=" + precio + ", Tipo=" +tipo+'}';
    }
    
    
    
}