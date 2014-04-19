package model.beans;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author skuarch
 */
@Entity
@Table(name = "test_beam")
public class TestBean implements Serializable{

    @Id
    @Column(name = "test_bean_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    
    @Column(name = "test_bean_name")
    private String name = null;
    
    //==========================================================================
    /**
     * Construct.
     */
    public TestBean() {

    } // end TestBean

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "name=" + name;
    }
    
} // end class
