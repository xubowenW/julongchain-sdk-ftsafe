/**
 * Copyright DingXuan. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bcia.javachain.sdk.security.msp.signer;

import org.bcia.javachain.sdk.security.csp.intfs.ICsp;
import org.bcia.javachain.sdk.security.csp.intfs.IKey;
import org.bcia.javachain.sdk.security.csp.intfs.opts.ISignerOpts;
import org.bcia.javachain.common.exception.JavaChainException;

/**
 * @author zhangmingyang
 * @Date: 2018/4/18
 * @company Dingxuan
 */
public class Signer implements ISigner {
    public ICsp csp;
    public IKey key;
    public Object pk;

    public Signer() {
    }

    public Signer(ICsp csp, IKey key, Object pk) {
        this.csp = csp;
        this.key = key;
        this.pk = pk;
    }

    public Signer newSigner(ICsp csp, IKey key){

        return new Signer(csp,key,pk);
    }
    @Override
    public Object publicKey() {
        return this.pk;
    }

    @Override
    public byte[] sign(IKey key, byte[] digest, ISignerOpts opts) {
        try {
            return this.csp.sign(this.key,digest,opts);
        } catch (JavaChainException e) {
            e.printStackTrace();
        }
        return null;
    }
}
