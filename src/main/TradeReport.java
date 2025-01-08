package main;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static main.TradeReportUtility.dateFormatter;

/**
 * This class generates trade report.
 */
public class TradeReport {

    public static void main(String[] args) {

        final List<TradeInstruction> tradeDetails = TradeReportUtility.getTradeInstructions();

        final Map<LocalDate, Double> outgoingSettlements = TradeReportUtility.calculateUSDSettlements(tradeDetails, InstructionType.BUY);

        final Map<LocalDate, Double> incomingSettlements = TradeReportUtility.calculateUSDSettlements(tradeDetails, InstructionType.SELL);

        final List<Map.Entry<String, Double>> outgoingRankings = TradeReportUtility.calculateRankings(tradeDetails, InstructionType.BUY);

        final List<Map.Entry<String, Double>> incomingRankings = TradeReportUtility.calculateRankings(tradeDetails, InstructionType.SELL);

        System.out.println("### Outgoing USD Settlements for dates ###");
        for (Map.Entry<LocalDate, Double> entry : outgoingSettlements.entrySet()) {
            final String date = entry.getKey().format(dateFormatter);
            final double amount = entry.getValue();
            System.out.println(String.format("%s - $(%.2f)", date, amount));
        }
        System.out.println("### End of Outgoing USD Settlements ###");
        System.out.println(" ");
        System.out.println("### Incoming USD Settlements for dates ###");
        for (Map.Entry<LocalDate, Double> entry : incomingSettlements.entrySet()) {
            final String date = entry.getKey().format(dateFormatter);
            final double amount = entry.getValue();
            System.out.println(String.format("%s - $(%.2f)", date, amount));
        }
        System.out.println("### End of Incoming USD Settlements ###");
        System.out.println("         ");
        System.out.println("### Ranking of Entities based on Outgoing USD Settlements ### ");
        int ranking = 1;
        for(Map.Entry<String,Double> entry : outgoingRankings) {
            System.out.println(String.format("%d) %s $(%.2f)", ranking ++, entry.getKey(), entry.getValue()));
        }
        System.out.println("### End of Ranking of Outgoing USD Settlements ###            ");

        System.out.println("         ");
        System.out.println("### Ranking of Entities based on Incoming USD Settlements ### ");
        ranking = 1;
        for(Map.Entry<String,Double> entry : incomingRankings) {
            System.out.println(String.format("%d) %s $(%.2f)", ranking ++, entry.getKey(), entry.getValue()));
        }
        System.out.println("### End of Ranking of Incoming USD Settlements ### ");
    }

}