package ru.academits.shagaev.range;

public class Range {
    private double from;
    private double to;

    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public double getLength() {
        return to - from;
    }

    public boolean isInside(double number) {
        return number >= from && number <= to;
    }

    public Range[] getIntersection(Range otherInterval) {
        double start = Math.max(from, otherInterval.from);
        double end = Math.min(to, otherInterval.to);

        if (start >= end) {
            return null;
        } else {
            return new Range[]{new Range(start, end)};
        }
    }

    public Range[] getUnion(Range otherInterval) {
        double start = Math.min(from, otherInterval.from);
        double end = Math.max(to, otherInterval.to);

        if (end < otherInterval.from || end < from || to < otherInterval.from || otherInterval.to < from) {
            return new Range[]{new Range(start, end), new Range(otherInterval.from, otherInterval.to)};
        } else {
            return new Range[]{new Range(start, end)};
        }
    }

    public Range[] getDifference(Range otherInterval) {
        if (to <= otherInterval.from || from >= otherInterval.to) {
            return new Range[]{new Range(from, to)};
        } else if (from < otherInterval.from && to > otherInterval.to) {
            return new Range[]{new Range(from, otherInterval.from), new Range(to, otherInterval.to)};
        } else if (from < otherInterval.from) {
            return new Range[]{new Range(from, otherInterval.from)};
        } else if (to > otherInterval.to) {
            return new Range[]{new Range(otherInterval.to, to)};
        } else {
            return new Range[]{};
        }
    }
}
