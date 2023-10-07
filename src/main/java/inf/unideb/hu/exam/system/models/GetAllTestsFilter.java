
package inf.unideb.hu.exam.system.models;

/**
 * Enum class for filtering {@link Test} entities.
 */
public enum GetAllTestsFilter {

    /**
     * All {@link Test} entities.
     */
    ALL,

    /**
     * Own {@link Test} entities.
     */
    OWN,

    /**
     * {@link Test} entities which is on collaborating.
     */
    COLLABORATING
}
