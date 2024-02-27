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

    public Range getIntersection(Range range) {
        double newFrom = Math.max(from, range.from);
        double newTo = Math.min(to, range.to);

        if (newFrom >= newTo) {
            return null;
        }

        return new Range(newFrom, newTo);
    }

    public Range[] getUnion(Range range) {
        double newRangeFrom = Math.min(from, range.from);
        double newRangeTo = Math.max(to, range.to);

        double minTo = Math.min(to, range.to);
        double maxFrom = Math.max(from, range.from);

        if (minTo < maxFrom) {
            return new Range[]{
                    new Range(newRangeFrom, minTo),
                    new Range(maxFrom, newRangeTo)
            };
        }

        return new Range[]{new Range(newRangeFrom, newRangeTo)};
    }

    public Range[] getDifference(Range range) {
        if (from >= range.to || to <= range.from) {
            return new Range[]{new Range(from, to)};
        } else if (from < range.from && to > range.to) {
            return new Range[]{new Range(from, range.from), new Range(range.to, to)};
        } else if (from < range.from) {
            return new Range[]{new Range(from, range.from)};
        } else if (to > range.to) {
            return new Range[]{new Range(range.to, to)};
        }

        return new Range[]{};
    }

    @Override
    public String toString() {
        return "(" + from + "; " + to + ")";
    }
}