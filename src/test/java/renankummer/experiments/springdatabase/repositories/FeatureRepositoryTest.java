package renankummer.experiments.springdatabase.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import renankummer.experiments.springdatabase.entities.Feature;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class FeatureRepositoryTest {
    @Autowired private FeatureRepository featureRepository;

    @Test
    void findAllEnabledFeatures_EmptyTable_ReturnsEmptyList() {
        var features = featureRepository.findAllEnabledFeatures();
        assertThat(features).isEmpty();
    }

    @Test
    @Sql("/data/FeatureRepository/only-disabled-features.sql")
    void findAllEnabledFeatures_OnlyDisabledFeatures_ReturnsEmptyList() {
        var features = featureRepository.findAllEnabledFeatures();
        assertThat(features).isEmpty();
    }

    @Test
    @Sql("/data/FeatureRepository/multiple-features.sql")
    void findAllEnabledFeatures_HasEnabledFeatures_ReturnsEnabledFeatures() {
        var features = featureRepository.findAllEnabledFeatures();

        assertAll(
                () -> assertThat(features).isNotEmpty(),
                () -> assertThat(features).allMatch(Feature::isEnabled)
        );
    }

    @Test
    void findAllDisabledFeatures_EmptyTable_ReturnsEmptyList() {
        var features = featureRepository.findAllDisabledFeatures();
        assertThat(features).isEmpty();
    }

    @Test
    @Sql("/data/FeatureRepository/only-enabled-features.sql")
    void findAllDisabledFeatures_OnlyEnabledFeatures_ReturnsEmptyList() {
        var features = featureRepository.findAllDisabledFeatures();
        assertThat(features).isEmpty();
    }

    @Test
    @Sql("/data/FeatureRepository/multiple-features.sql")
    void findAllDisabledFeatures_HasDisabledFeatures_ReturnsDisabledFeatures() {
        var features = featureRepository.findAllDisabledFeatures();

        assertAll(
                () -> assertThat(features).isNotEmpty(),
                () -> assertThat(features).noneMatch(Feature::isEnabled)
        );
    }

    @Test
    void enableSingleFeature_EmptyTable_DoesNothing() {
        var featureName = UUID.randomUUID().toString();
        var enabledCount = featureRepository.enableSingleFeature(featureName);

        assertThat(enabledCount).isZero();
    }

    @Test
    @Sql("/data/FeatureRepository/multiple-features.sql")
    void enableSingleFeature_FeatureNotFound_DoesNothing() {
        var featureName = UUID.randomUUID().toString();
        var enabledCount = featureRepository.enableSingleFeature(featureName);

        assertThat(enabledCount).isZero();
    }

    @Test
    @Sql("/data/FeatureRepository/multiple-features.sql")
    void enableSingleFeature_FeatureExists_EnablesFeature() {
        var expectedEnabledFeature = "Fast Travel";
        var expectedDisabledFeatures = List.of("Customizations","Weapon Crafting","Upgrading Stats");
        var expectedUpdateCount = 3;

        var updateCount = featureRepository.enableSingleFeature(expectedEnabledFeature);
        var enabledFeatures = featureRepository.findAllEnabledFeatures();
        var disabledFeatures = featureRepository.findAllDisabledFeatures();

        assertAll(
                () -> assertThat(updateCount).isEqualTo(expectedUpdateCount),
                () -> assertThat(enabledFeatures)
                        .extracting(Feature::getName)
                        .containsExactly(expectedEnabledFeature),
                () -> assertThat(disabledFeatures)
                        .extracting(Feature::getName)
                        .containsExactlyElementsOf(expectedDisabledFeatures)
        );
    }
}
