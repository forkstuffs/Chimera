/*
 * Copyright (C) 2017 Karus Labs
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.karuslabs.commons.menu;


public class Region {
    
    private int length;
    private int min, max;
    
    
    public Region(int length, int min, int max) {
        this.length = length;
        this.min = min;
        this.max = max;
    }
    
    
    public boolean inRegion(int slot) {
        int row = slot % length;
        double coloumn = slot / (double) length;
        
        boolean withinLength = row >= min % length && row <= max % length;
        boolean withinWidth = coloumn >= min / length && coloumn <= max / length;
        
        return withinLength && withinWidth;
    }
    
    
    public int getMin() {
        return min;
    }
    
    public int getMax() {
        return max;
    }
    
}