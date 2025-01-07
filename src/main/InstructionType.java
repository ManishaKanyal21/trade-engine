package main;

import java.util.stream.Stream;

enum InstructionType {
    BUY("B"), SELL("S");

    private final String name;

    InstructionType(final String name) {
        this.name = name;
    }

    static InstructionType fromType(String instructionType) {
        return Stream.of(InstructionType.values()).filter(value -> value.name.equals(instructionType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Instruction type provided is invalid."));
    }
}
