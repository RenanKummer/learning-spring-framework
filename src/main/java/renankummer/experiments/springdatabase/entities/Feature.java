package renankummer.experiments.springdatabase.entities;

import javax.persistence.*;

@Entity
@Table(name = "features")
public class Feature {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "enabled", nullable = false)
    private boolean isEnabled;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
