package main;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

import static main.TradeReportUtility.dateFormatter;

/**
 * Class that holds trade Instruction
 */
final class TradeInstruction {

    private final String entityName;
    private final InstructionType instructionType;
    private final double agreedFx;
    private final LocalDate instructionDate;
    private final String currency;
    private final int numberOfUnits;
    private final double pricePerUnit;
    private final LocalDate actualSettlementDate;

    /**
     * @param entityName               name of the entity
     * @param instructionType          "B" or "S"
     * @param agreedFx                 exchange rate
     * @param currency                 name of the currency
     * @param instructionDate          date when the transaction was instructed
     * @param instructedSettlementDate date when transaction is instructed to be settled
     * @param numberOfUnits            number of units to buy or sell
     * @param pricePerUnit             price of a unit
     */
  TradeInstruction(final String entityName, final String instructionType, final double agreedFx,
                            final String currency, final String instructionDate,
                            final String instructedSettlementDate,
                            final int numberOfUnits, final double pricePerUnit) {
        Objects.requireNonNull(entityName, "Entity name should be provided.");
        Objects.requireNonNull(entityName, "Instruction type should be provided.");
        Objects.requireNonNull(entityName, "Currency should be provided.");
        Objects.requireNonNull(entityName, "Settlement date should be provided.");

        this.entityName = entityName;
        this.instructionType = InstructionType.fromType(instructionType);
        this.agreedFx = agreedFx;
        this.instructionDate = LocalDate.parse(instructionDate, dateFormatter);
        this.numberOfUnits = numberOfUnits;
        this.pricePerUnit = pricePerUnit;
        this.currency = currency;
        this.actualSettlementDate = calculateActualSettlementDate(instructedSettlementDate, currency);
    }

    /**
     * This method calculates the working day when settlement can take place based on the instructed settlement date and currency.
     *
     * @param instructedSettlementDate - date instructed by the client for settlement of trade.
     * @param currency                 - currency of the trade
     * @return actual date when settlement will take place.
     */
    private LocalDate calculateActualSettlementDate(final String instructedSettlementDate, final String currency) {
        final LocalDate settlementDate = LocalDate.parse(instructedSettlementDate, dateFormatter);
        final DayOfWeek dayOfWeek = settlementDate.getDayOfWeek();
        if ("AED".equalsIgnoreCase(currency) || "SAR".equalsIgnoreCase(currency)) {
            if (DayOfWeek.FRIDAY.equals(dayOfWeek)) {
                return settlementDate.plusDays(2);
            } else if (DayOfWeek.SATURDAY.equals(dayOfWeek)) {
                return settlementDate.plusDays(1);
            }
        } else {
            if (DayOfWeek.SATURDAY.equals(dayOfWeek)) {
                return settlementDate.plusDays(2);
            } else if (DayOfWeek.SUNDAY.equals(dayOfWeek)) {
                return settlementDate.plusDays(1);
            }
        }
        return settlementDate;
    }

    String getEntityName() {
        return entityName;
    }

    InstructionType getInstructionType() {
        return instructionType;
    }

    LocalDate getActualSettlementDate() {
        return actualSettlementDate;
    }

    LocalDate getInstructionDate() {
        return instructionDate;
    }

    String getCurrency() {
        return currency;
    }

    double calculateUSDAmount() {
        return pricePerUnit * numberOfUnits * agreedFx;
    }
}
