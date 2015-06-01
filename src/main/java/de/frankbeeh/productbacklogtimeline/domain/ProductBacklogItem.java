package de.frankbeeh.productbacklogtimeline.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProductBacklogItem.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PRODUCTBACKLOGITEM")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductBacklogItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "pbi_key", length = 20, nullable = false)
    private String pbiKey;

    @Size(max = 100)
    @Column(name = "title", length = 100)
    private String title;

    @Size(max = 2000)
    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "estimate", precision=10, scale=2)
    private BigDecimal estimate;

    @Column(name = "state")
    private String state;

    @ManyToOne
    private ProductTimestamp productTimestamp;

    public ProductBacklogItem(String pbiKey, String title, String description,
			BigDecimal estimate, String state) {
    	this.pbiKey = pbiKey;
    	this.title = title;
		this.description = description;
		this.estimate = estimate;
		this.state = state;
	}

	public ProductBacklogItem() {
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPbiKey() {
        return pbiKey;
    }

    public void setPbiKey(String pbiKey) {
        this.pbiKey = pbiKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getEstimate() {
        return estimate;
    }

    public void setEstimate(BigDecimal estimate) {
        this.estimate = estimate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ProductTimestamp getProductTimestamp() {
        return productTimestamp;
    }

    public void setProductTimestamp(ProductTimestamp productTimestamp) {
        this.productTimestamp = productTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductBacklogItem productBacklogItem = (ProductBacklogItem) o;

        if ( ! Objects.equals(id, productBacklogItem.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductBacklogItem{" +
                "id=" + id +
                ", pbiKey='" + pbiKey + "'" +
                ", title='" + title + "'" +
                ", description='" + description + "'" +
                ", estimate='" + estimate + "'" +
                ", state='" + state + "'" +
                '}';
    }
}
