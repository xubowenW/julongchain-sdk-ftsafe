/*
 *  Copyright 2016 DTCC, Fujitsu Australia Software Technology, IBM - All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.bcia.javachain.sdk.transaction;


import org.bcia.javachain.sdk.exception.InvalidArgumentException;
import org.bcia.javachain.sdk.exception.ProposalException;
import org.bcia.javachain.sdk.transaction.InstantiateProposalBuilder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class InstantiateProposalBuilderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void testSetTransientMapNull() throws Exception {

        thrown.expect(InvalidArgumentException.class);
        thrown.expectMessage("Transient map may not be null");

        InstantiateProposalBuilder builder = InstantiateProposalBuilder.newBuilder();
        builder.setTransientMap(null);

    }

    @Test
    public void testBuild() throws Exception {

        thrown.expect(ProposalException.class);
        thrown.expectMessage("IO Error");

        InstantiateProposalBuilder builder = InstantiateProposalBuilder.newBuilder();
        builder.build();

    }

    @Test
    public void testInvalidType() throws Exception {

        thrown.expect(InvalidArgumentException.class);
        thrown.expectMessage("SmartContract type is required");

        InstantiateProposalBuilder builder = InstantiateProposalBuilder.newBuilder();
        builder.smartContractType(null);

        builder.build();
    }


}
