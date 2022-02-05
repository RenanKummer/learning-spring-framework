package renankummer.experiments.springdatabase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import renankummer.experiments.springdatabase.entities.Feature;

import java.util.List;

public interface FeatureRepository extends JpaRepository<Feature, Long> {
    @Query("from Feature f where f.isEnabled = true")
    List<Feature> findAllEnabledFeatures();
    @Query("from Feature f where f.isEnabled = false")
    List<Feature> findAllDisabledFeatures();

    @Modifying
    @Query("""
        update Feature f
        set f.isEnabled =
            case
                when f.isEnabled = true then false
                else true
            end
        where exists (
            select 1 from Feature where name = :name
        ) and (
            f.name = :name
            or f.isEnabled = true
        )
        """)
    int enableSingleFeature(@Param("name") String name);
}
