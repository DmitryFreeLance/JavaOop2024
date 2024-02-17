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

    public Range getIntersection(Range range2) {
        double newFrom = Math.max(this.from, range2.from);
        double newTo = Math.min(to, range2.to);

        if (newFrom >= newTo) {
            return null;
        }

        return new Range(newFrom, newTo);
    }

    public Range[] getUnion(Range range2) {
        double newFrom = Math.min(this.from, range2.from);
        double newTo = Math.max(this.to, range2.to);

        if (Math.min(this.to, range2.to) < Math.max(this.from, range2.from)) {
            return new Range[]{new Range(newFrom, Math.min(this.to, range2.to)),
                    new Range(Math.max(this.from, range2.from), newTo)};
        }

        return new Range[]{new Range(newFrom, newTo)};
    }

    public Range[] getDifference(Range range2) {
        if (to <= range2.from || from >= range2.to) {
            return new Range[]{new Range(from, range2.from), new Range(to, range2.to)};
        } else if (from < range2.from && to > range2.to) {
            return new Range[]{new Range(from, range2.from), new Range(range2.to, to)};
        } else if (from < range2.from) {
            return new Range[]{new Range(from, range2.from), new Range(to, range2.to)};
        } else if (to > range2.to) {
            return new Range[]{new Range(range2.to, to)};
        }

        return new Range[]{};
    }

    @Override
    public String toString() {
        return ("[" + getFrom() + ", " + getTo() + "]");
    }
}
