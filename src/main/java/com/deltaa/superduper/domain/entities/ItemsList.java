package com.deltaa.superduper.domain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "items_list")
public class ItemsList extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "items_list_gen")
    @SequenceGenerator(name = "items_list_gen", sequenceName = "items_list_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Min(3)
    private String name;

    @OneToMany(mappedBy = "itemsList", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private final List<Item> items = new ArrayList<Item>();

    ItemsList() {}
    public ItemsList(final String name) {
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void addItem(final Item item) {
        getItems().add(item);
        item.setItemsList(this);
    }

    public void addItems(List<Item> items) {
        for (Item item : items) {
            addItem(item);
        }
    }

    public List<Item> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(1, 3)
            .append(id)
            .append(name)
            .toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        ItemsList rhs = (ItemsList) obj;
        return new EqualsBuilder()
            .append(id, rhs.id)
            .append(name, rhs.name)
            .isEquals();
    }

}
