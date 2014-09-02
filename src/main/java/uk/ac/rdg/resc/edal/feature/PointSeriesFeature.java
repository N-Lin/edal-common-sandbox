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

package uk.ac.rdg.resc.edal.feature;

import java.util.Map;

import org.joda.time.DateTime;

import uk.ac.rdg.resc.edal.domain.Extent;
import uk.ac.rdg.resc.edal.grid.TimeAxis;
import uk.ac.rdg.resc.edal.metadata.Parameter;
import uk.ac.rdg.resc.edal.position.HorizontalPosition;
import uk.ac.rdg.resc.edal.position.VerticalPosition;
import uk.ac.rdg.resc.edal.util.Array1D;

/**
 * A measurement of a time series at a point
 * 
 * @author Guy Griffiths
 * @author Jon Blower
 */
public class PointSeriesFeature extends AbstractDiscreteFeature<DateTime, Extent<DateTime>> {

    private final HorizontalPosition hPos;
    private final VerticalPosition zPos;

    public PointSeriesFeature(String id, String name, String description, TimeAxis domain,
            HorizontalPosition hPos, VerticalPosition zPos, Map<String, Parameter> parameters,
            Map<String, Array1D<Number>> values) {
        super(id, name, description, domain, parameters, values);
        this.hPos = hPos;
        this.zPos = zPos;
    }

    /**
     * Gets the horizontal location of this point series feature.
     */
    public HorizontalPosition getHorizontalPosition() {
        return hPos;
    }

    /**
     * Gets the vertical location of this point series feature.
     */
    public VerticalPosition getVerticalPosition() {
        return zPos;
    }

    /**
     * Gets the {@link TimeAxis} which makes up this domain
     */
    @Override
    public TimeAxis getDomain() {
        return (TimeAxis) super.getDomain();
    }
    
    @Override
    public Array1D<Number> getValues(String paramId) {
        return (Array1D<Number>) super.getValues(paramId);
    }
}
