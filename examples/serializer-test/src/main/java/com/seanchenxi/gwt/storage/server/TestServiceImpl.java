/*
 * Copyright 2013 Xi CHEN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.seanchenxi.gwt.storage.server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.seanchenxi.gwt.storage.client.service.TestService;
import com.seanchenxi.gwt.storage.shared.RpcTestMapKey;
import com.seanchenxi.gwt.storage.shared.RpcTestMapValue;
import com.seanchenxi.gwt.storage.shared.RpcTestValue;

/**
 * Created by: Xi
 */
public class TestServiceImpl extends RemoteServiceServlet implements TestService {

  @Override
  public RpcTestValue getRpcTestValue(){
    return new RpcTestValue(12345L);
  }

  @Override
  public List<RpcTestValue> getRpcTestValueList(){
    LinkedList<RpcTestValue> rpcTestValues = new LinkedList<RpcTestValue>();
    rpcTestValues.add(new RpcTestValue(234567L));
    rpcTestValues.add(new RpcTestValue(456789L));
    return rpcTestValues;
  }

  @Override
  public Map<RpcTestMapKey, RpcTestMapValue> getRpcTestValueStringMap(){
    HashMap<RpcTestMapKey, RpcTestMapValue> rpcTestMapKeyRpcTestMapValueHashMap = new HashMap<RpcTestMapKey, RpcTestMapValue>();
    rpcTestMapKeyRpcTestMapValueHashMap.put(new RpcTestMapKey(RpcTestMapKey.MapKey.KEY_1), new RpcTestMapValue(new RpcTestMapValue.MapValue("KEY_1_VALUE")));
    rpcTestMapKeyRpcTestMapValueHashMap.put(new RpcTestMapKey(RpcTestMapKey.MapKey.KEY_2), new RpcTestMapValue(new RpcTestMapValue.MapValue("KEY_2_VALUE")));
    return rpcTestMapKeyRpcTestMapValueHashMap;
  }
}
