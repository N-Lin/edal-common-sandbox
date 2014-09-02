/*******************************************************************************
 * Copyright (c) 2012 The University of Reading
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the University of Reading, nor the names of the
 *    authors or contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/

package uk.ac.rdg.resc.edal.position;

/**
 * Implementation of a {@link VerticalCrs}
 * 
 * @author Guy
 */
public class VerticalCrsImpl implements VerticalCrs {
    private static final long serialVersionUID = 1L;
    
    private String units;
    private boolean pressure;
    private boolean dimensionless;
    private boolean positiveUpwards;

    public VerticalCrsImpl(String units, boolean pressure, boolean dimensionless,
            boolean positiveUpwards) {
        this.units = units;
        this.pressure = pressure;
        this.dimensionless = dimensionless;
        this.positiveUpwards = positiveUpwards;
    }

    @Override
    public String getUnits() {
        return units;
    }

    @Override
    public boolean isPressure() {
        return pressure;
    }

    @Override
    public boolean isDimensionless() {
        return dimensionless;
    }

    @Override
    public boolean isPositiveUpwards() {
        return positiveUpwards;
    }

    @Override
    public String toString() {
        return "Units: " + units + ", Positive upwards: " + positiveUpwards + ", Pressure: "
                + pressure + ", Dimensionless: " + dimensionless;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (dimensionless ? 1231 : 1237);
        result = prime * result + (positiveUpwards ? 1231 : 1237);
        result = prime * result + (pressure ? 1231 : 1237);
        result = prime * result + ((units == null) ? 0 : units.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        VerticalCrsImpl other = (VerticalCrsImpl) obj;
        if (dimensionless != other.dimensionless)
            return false;
        if (positiveUpwards != other.positiveUpwards)
            return false;
        if (pressure != other.pressure)
            return false;
        if (units == null) {
            if (other.units != null)
                return false;
        } else if (!units.equals(other.units))
            return false;
        return true;
    }
}
