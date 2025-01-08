package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

/**
 * This class has helper methods that are used repetitively.
 */
final class TradeReportUtility {

    final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

    /**
     * This method calculates the incoming or outgoing amount settled in USD for all the settlement dates
     *
     * @param tradeInstructions
     * @param instructionType
     * @return
     */
    static Map<LocalDate, Double> calculateUSDSettlements(final List<TradeInstruction> tradeInstructions, final InstructionType instructionType) {
        Objects.requireNonNull(tradeInstructions, "Trade instructions should not be null.");
        return tradeInstructions.stream()
                .filter(trade -> instructionType.equals(trade.getInstructionType()))
                .collect(groupingBy(TradeInstruction::getActualSettlementDate, summingDouble(TradeInstruction::calculateUSDAmount)));
    }

    /**
     * This method calculates the rankings of the entities based on incoming or outgoing amount
     *
     * @param tradeInstructions
     * @param instructionType
     * @return
     */
    static List<Map.Entry<String, Double>> calculateRankings(final List<TradeInstruction> tradeInstructions, final InstructionType instructionType) {
        Objects.requireNonNull(tradeInstructions, "Trade instructions should not be null.");
        return tradeInstructions.stream()
                .filter(trade -> instructionType.equals(trade.getInstructionType()))
                .sorted(Comparator.comparingDouble(TradeInstruction::calculateUSDAmount).reversed())
                .map(t -> Map.entry(t.getEntityName(), t.calculateUSDAmount()))
                .collect(Collectors.toList());
    }

    /**
     * This method creates the sample trade instruction data sent by clients
     *
     * @return
     */
    static List<TradeInstruction> getTradeInstructions() {
        final TradeInstruction tradeInstruction1 = new TradeInstruction("zoo", "B", 0.50,
                "SGP", "01 Jan 2016", "01 Jan 2016", 100, 100.5);// Friday

        final TradeInstruction tradeInstruction2 = new TradeInstruction("foo", "B", 0.50,
                "SGP", "01 Jan 2016", "02 Jan 2016", 200, 100.5);// Saturday - will settle on Monday

        final TradeInstruction tradeInstruction3 = new TradeInstruction("bar", "B", 0.50,
                "SGP", "01 Jan 2016", "03 Jan 2016", 300, 100.5);// Sunday - will settle on Monday

        final TradeInstruction tradeInstruction4 = new TradeInstruction("moo", "B", 0.50,
                "SGP", "01 Jan 2016", "04 Jan 2016", 400, 100.5);// Monday

        final TradeInstruction tradeInstruction5 = new TradeInstruction("doo", "B", 0.50,
                "SGP", "01 Jan 2016", "05 Jan 2016", 500, 100.5);// Tuesday

        final TradeInstruction tradeInstruction6 = new TradeInstruction("bar", "S", 0.22,
                "AED", "05 Jan 2016", "07 Jan 2016", 100, 150.5);// Thursday

        final TradeInstruction tradeInstruction7 = new TradeInstruction("foo", "S", 0.22,
                "AED", "05 Jan 2016", "08 Jan 2016", 200, 150.5);// Friday - will settle on Sunday

        final TradeInstruction tradeInstruction8 = new TradeInstruction("zoo", "S", 0.22,
                "AED", "06 Jan 2016", "09 Jan 2016", 300, 150.5);// Saturday - will settle on Sunday

        final TradeInstruction tradeInstruction9 = new TradeInstruction("moo", "S", 0.22,
                "AED", "06 Jan 2016", "10 Jan 2016", 400, 150.5);// Sunday

        final TradeInstruction tradeInstruction10 = new TradeInstruction("doo", "S", 0.22,
                "AED", "06 Jan 2016", "11 Jan 2016", 500, 150.5);// Monday

        return List.of(tradeInstruction1, tradeInstruction2, tradeInstruction3, tradeInstruction4, tradeInstruction5, tradeInstruction6, tradeInstruction7, tradeInstruction8, tradeInstruction9, tradeInstruction10);
    }

    private TradeReportUtility() {
    }
}
