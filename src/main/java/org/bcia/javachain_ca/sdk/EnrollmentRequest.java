/*
 *  Copyright 2017 DTCC, Fujitsu Australia Software Technology, IBM - All Rights Reserved.
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

package org.bcia.javachain_ca.sdk;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;

import org.bcia.javachain_ca.sdk.exception.InvalidArgumentException;

/**
 * An enrollment request is information required to enroll the user with member service.
 */
public class EnrollmentRequest {

    // A PEM-encoded string containing the CSR (Certificate Signing Request) based on PKCS #10
    private String csr;
    // Comma-separated list of host names to associate with the certificate
    private Collection<String> hosts = new ArrayList<>();
    // Name of the signing profile to use when issuing the certificate
    private String profile = null;
    // Label used in HSM operations
    private String label = null;
    // Key pair for generating certification request
    private KeyPair keypair = null;
    // The Certificate Authority's name
    private String caName;

    // Attribute requests. added v1.1
    private Map<String, AttrReq> attrreqs = null; //new HashMap<>();

    private String password; //用户密码
    private String CN; //通用名CN，必填
    private String O; //组织机构名称
    private String OU; //部门
    private String L; //城市
    private String S; //省份
    private String C; //ISO 3166国家代码
    private String E; //邮箱地址
    private String processId; //实体证书流程ID
    private String certType; //获取证书类型1 : Certificate 公钥证书;2 : PKCS12 P12证书;3: JKS JKS证书
    private String reqType; //请求类型：1 : 证书申请; 2 : 证书更新
    private String keyType; //密钥类型

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCN() {
        return CN;
    }

    public void setCN(String CN) {
        this.CN = CN;
    }

    public String getO() {
        return O;
    }

    public void setO(String o) {
        O = o;
    }

    public String getOU() {
        return OU;
    }

    public void setOU(String OU) {
        this.OU = OU;
    }

    public String getL() {
        return L;
    }

    public void setL(String l) {
        L = l;
    }

    public String getS() {
        return S;
    }

    public void setS(String s) {
        S = s;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getE() {
        return E;
    }

    public void setE(String e) {
        E = e;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    /**
     * The certificate signing request if it's not supplied it will be generated.
     *
     * @param csr
     */

    public void setCsr(String csr) {
        this.csr = csr;
    }

    // Constructor
    public EnrollmentRequest() {

    }

    String getCsr() {
        return csr;
    }

    /**
     * EnrollmentRequest All the fields are optional
     *
     * @param profile
     * @param label
     * @param keypair Keypair used to sign or create the certificate if needed.
     */
    public EnrollmentRequest(String profile, String label, KeyPair keypair) {
        this.profile = profile;
        this.label = label;
        this.keypair = keypair;
    }

    KeyPair getKeyPair() {
        return keypair;
    }

    /**
     * The Key pair to create the signing certificate if not supplied it will be generated.
     *
     * @param keypair
     */
    public void setKeyPair(KeyPair keypair) {
        this.keypair = keypair;
    }

    void setCSR(String csr) {
        this.csr = csr;
    }

    void setCAName(String caName) {
        this.caName = caName;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Collection<String> getHosts() {
        return new ArrayList<>(hosts);
    }

    public void addHost(String host) {
        this.hosts.add(host);
    }

    // Convert the enrollment request to a JSON string
    String toJson() {
        StringWriter stringWriter = new StringWriter();
        JsonWriter jsonWriter = Json.createWriter(new PrintWriter(stringWriter));
        jsonWriter.writeObject(toJsonObject());
        jsonWriter.close();
        return stringWriter.toString();
    }

    // Convert the enrollment request to a JSON object
    private JsonObject toJsonObject() {
        JsonObjectBuilder factory = Json.createObjectBuilder();
        if (profile != null) {
            factory.add("profile", profile);
        }
        if (!hosts.isEmpty()) {
            JsonArrayBuilder ab = Json.createArrayBuilder();
            for (String host : hosts) {
                ab.add(host);
            }
            factory.add("hosts", ab.build());
        }
        if (label != null) {
            factory.add("label", label);
        }

        if (caName != null) {
            factory.add(HFCAClient.FABRIC_CA_REQPROP, caName);
        }
        factory.add("certificate_request", csr);

        if (attrreqs != null) {
            JsonArrayBuilder ab = Json.createArrayBuilder();
            for (AttrReq attrReq : attrreqs.values()) {
                JsonObjectBuilder i = Json.createObjectBuilder();
                i.add("name", attrReq.name);
                if (attrReq.optional != null) {
                    i.add("optional", attrReq.optional);
                }
                ab.add(i);

            }
            factory.add("attr_reqs", ab.build());
        }

        return factory.build();
    }

    /**
     * Add attribute request to ensure no attributes are in the certificate - not even default ones.
     *
     * @throws InvalidArgumentException
     */
    public void addAttrReq() throws InvalidArgumentException {
        if (attrreqs != null && !attrreqs.isEmpty()) {
            throw new InvalidArgumentException("Attributes have already been defined.");

        }
        attrreqs = new HashMap<>();
    }

    /**
     * Add attribute to certificate.
     *
     * @param name Name of attribute.
     * @return Attribute added.
     * @throws InvalidArgumentException
     */
    public AttrReq addAttrReq(String name) throws InvalidArgumentException {
        if (name == null || name.isEmpty()) {
            throw new InvalidArgumentException("name may not be null or empty.");

        }
        return new AttrReq(name);
    }

    public class AttrReq {
        final String name;
        Boolean optional = null;

        AttrReq(String name) {
            this.name = name;
            if (attrreqs == null) {
                attrreqs = new HashMap<>();
            }
            attrreqs.put(name, this);
        }

        public AttrReq setOptional(boolean optional) {
            this.optional = optional;
            return this;
        }
    }
}
