package com.deltaa.superduper.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "item_reminders")
public class Reminder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "item_reminder_gen")
    @SequenceGenerator(name = "item_reminder_gen", sequenceName = "item_reminder_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Min(3)
    private String description;

    Reminder() {}

    public Reminder(final String description) {
        this.description = description;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    @JsonBackReference
    private Item item;

    @Override
    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(1, 3)
            .append(id)
            .append(description)
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
        Reminder rhs = (Reminder) obj;
        return new EqualsBuilder()
            .append(id, rhs.id)
            .append(description, rhs.description)
            .isEquals();
    }

}
