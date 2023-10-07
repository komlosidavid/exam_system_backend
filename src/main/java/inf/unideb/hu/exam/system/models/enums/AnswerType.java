
package inf.unideb.hu.exam.system.models.enums;

import inf.unideb.hu.exam.system.models.Answer;

/**
 * Enum class for holding {@link Answer} entity type.
 */
public enum AnswerType {

    /**
     * Represents radio buttons.
     */
    ONE_ANSWER,

    /**
     * Represents checkboxes.
     */
    MULTIPLE_ANSWERS,

    /**
     * Represents textarea.
     */
    EXPLANATORY_ANSWER
}
