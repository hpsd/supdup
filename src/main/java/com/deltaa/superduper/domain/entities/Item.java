package com.deltaa.superduper.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "items")
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "items_gen")
    @SequenceGenerator(name = "items_gen", sequenceName = "items_seq", allocationSize = 1)
    private Long id;

    @NotNull
    private String name;
    private String tag;
    private boolean completed;
    private boolean deleted;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "items_list_id")
    @JsonBackReference
    private ItemsList itemsList;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private final List<Reminder> reminders = new ArrayList<>();

    Item() {}

    public Item(final String name) {
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public ItemsList getItemsList() {
        return itemsList;
    }

    public void setItemsList(ItemsList itemsList) {
        this.itemsList = itemsList;
    }

    public List<Reminder> getReminders() {
        return reminders;
    }

    public void addReminder(final Reminder reminder) {
        getReminders().add(reminder);
        reminder.setItem(this);
    }

    public void addReminders(final List<Reminder> reminders) {
        for(Reminder reminder : reminders) {
            getReminders().add(reminder);
            reminder.setItem(this);
        }
    }

    public void markComplete() {
       this.completed = true;
    }

    public void markDeleted() {
       this.deleted = true;
    }

    public void restore() {
       this.deleted = false;
    }

    public void tag(String tag) {
       this.tag = tag;
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
        Item rhs = (Item) obj;
        return new EqualsBuilder()
            .append(id, rhs.id)
            .append(name, rhs.name)
            .isEquals();
    }

}
