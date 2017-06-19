/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.nure.rumiantsev.SummaryTask4.Utils;

import java.util.Comparator;
import ua.nure.rumiantsev.SummaryTask4.Entity.Course;

/**
 *<p>Class, producing comparators for {@link ua.nure.rumiantsev.SummaryTask4.Entity.Course Course}</p>
 * @author Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
 */
public class CourseComparatorFactory {
    /**
     * Returns new instance of requested comparator if any found
     * @param sort type of required comparator
     * @return new instance of required comparator
     * @throws IllegalArgumentException if no appropriate comparator tye was found
     */
    public static Comparator<Course> getComparator(String sort) throws IllegalArgumentException{
        switch (sort.toLowerCase()){
            case "az": return new AzComparator();
            case "za": return new ZaComparator();
            case "capacity": return new CapacityComparator();
            case "term": return new TermComparator();
            default: throw new IllegalArgumentException();
        }
    } 
    /**
     * Name alphabet sorting comparator
     */
    private static class AzComparator implements Comparator<Course>{

        @Override
        public int compare(Course o1, Course o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
    /**
     * Name reverse alphabet sorting comparator
     */
    private static class ZaComparator implements Comparator<Course>{

        @Override
        public int compare(Course o1, Course o2) {
            return -o1.getName().compareTo(o2.getName());
        }
    }
    /**
     * Capacity ascending sorting comparator
     */
    private static class CapacityComparator implements Comparator<Course>{

        @Override
        public int compare(Course o1, Course o2) {
            return Integer.compare(o1.getCapacity(),o2.getCapacity());
        }
    }
    /**
     * Term ascending sorting comparator
     */
    private static class TermComparator implements Comparator<Course>{

        @Override
        public int compare(Course o1, Course o2) {
            return Long.compare(o1.getTerm(),o2.getTerm());
        }
    }
}
