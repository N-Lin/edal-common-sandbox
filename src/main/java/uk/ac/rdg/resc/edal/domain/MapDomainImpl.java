/*******************************************************************************
 * Copyright (c) 2013 The University of Reading
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

package uk.ac.rdg.resc.edal.domain;

import org.joda.time.DateTime;
import org.opengis.metadata.extent.GeographicBoundingBox;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import uk.ac.rdg.resc.edal.geometry.BoundingBox;
import uk.ac.rdg.resc.edal.grid.GridCell2D;
import uk.ac.rdg.resc.edal.grid.HorizontalGrid;
import uk.ac.rdg.resc.edal.position.HorizontalPosition;
import uk.ac.rdg.resc.edal.position.VerticalCrs;
import uk.ac.rdg.resc.edal.util.Array;
import uk.ac.rdg.resc.edal.util.GridCoordinates2D;

/**
 * Implementation of a {@link MapDomain}
 * 
 * @author Guy
 */
public class MapDomainImpl implements MapDomain {
    private HorizontalGrid hGrid;
    private Double z;
    private VerticalCrs vCrs;
    private DateTime time;

    public MapDomainImpl(HorizontalGrid hGrid, Double z, VerticalCrs vCrs, DateTime time) {
        this.hGrid = hGrid;
        this.z = z;
        this.vCrs = vCrs;
        this.time = time;
    }

    @Override
    public Double getZ() {
        return z;
    }

    @Override
    public VerticalCrs getVerticalCrs() {
        return vCrs;
    }

    @Override
    public DateTime getTime() {
        return time;
    }

    @Override
    public long size() {
        return hGrid.size();
    }

    @Override
    public int getXSize() {
        return hGrid.getXSize();
    }

    @Override
    public int getYSize() {
        return hGrid.getYSize();
    }

    @Override
    public GridCoordinates2D findIndexOf(HorizontalPosition position) {
        return hGrid.findIndexOf(position);
    }

    @Override
    public Array<GridCell2D> getDomainObjects() {
        return hGrid.getDomainObjects();
    }

    @Override
    public boolean contains(HorizontalPosition position) {
        return hGrid.contains(position);
    }

    @Override
    public BoundingBox getBoundingBox() {
        return hGrid.getBoundingBox();
    }

    @Override
    public GeographicBoundingBox getGeographicBoundingBox() {
        return hGrid.getGeographicBoundingBox();
    }

    @Override
    public CoordinateReferenceSystem getCoordinateReferenceSystem() {
        return hGrid.getCoordinateReferenceSystem();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((hGrid == null) ? 0 : hGrid.hashCode());
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        result = prime * result + ((vCrs == null) ? 0 : vCrs.hashCode());
        result = prime * result + ((z == null) ? 0 : z.hashCode());
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
        MapDomainImpl other = (MapDomainImpl) obj;
        if (hGrid == null) {
            if (other.hGrid != null)
                return false;
        } else if (!hGrid.equals(other.hGrid))
            return false;
        if (time == null) {
            if (other.time != null)
                return false;
        } else if (!time.equals(other.time))
            return false;
        if (vCrs == null) {
            if (other.vCrs != null)
                return false;
        } else if (!vCrs.equals(other.vCrs))
            return false;
        if (z == null) {
            if (other.z != null)
                return false;
        } else if (!z.equals(other.z))
            return false;
        return true;
    }
}
