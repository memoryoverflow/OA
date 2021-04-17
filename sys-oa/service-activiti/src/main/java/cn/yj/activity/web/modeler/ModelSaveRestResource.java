/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.yj.activity.web.modeler;

import cn.yj.commons.utils.StringUtils;
import cn.yj.entity.R;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * <br>
 * 重写这个 流程图保存接口，因为前端用的是FormData 格式。而它接收的Body的参数。所以参数无法绑定。
 * 重写接收参数
 *
 * @author 永健
 * @since 2020-12-08 16:23
 */
//@RestController
public class ModelSaveRestResource implements ModelDataJsonConstants
{
    protected static final Logger LOGGER = LoggerFactory.getLogger(ModelSaveRestResource.class);

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    public ModelSaveRestResource()
    {
    }

    @PutMapping("/model/{modelId}/save")
    public R saveModel(@PathVariable String modelId, @RequestBody Map<String, String> values)
    {
        ByteArrayOutputStream outStream = null;
        try
        {

            String jsonXml = values.get("json_xml");
            JSONObject jsonObject = JSON.parseObject(jsonXml);
            JSONObject properties = JSON.parseObject(jsonObject.get("properties").toString());
            if (!properties.containsKey("process_id") || StringUtils.isBlank(properties.get("process_id").toString()))
            {
                return R.error("请填写流程名称");
            }
            Model model = this.repositoryService.getModel(modelId);
            ObjectNode modelJson = (ObjectNode) this.objectMapper.readTree(model.getMetaInfo());
            modelJson.put("name", values.get("name"));
            modelJson.put("description", values.get("description"));
            model.setMetaInfo(modelJson.toString());
            model.setName((String) values.get("name"));
            this.repositoryService.saveModel(model);
            this.repositoryService.addModelEditorSource(model.getId(), jsonXml.getBytes("utf-8"));
            this.repositoryService.addModelEditorSource(model.getId(), values.get("json_xml").getBytes("utf-8"));
            InputStream svgStream = new ByteArrayInputStream((values.get("svg_xml")).getBytes("utf-8"));
            TranscoderInput input = new TranscoderInput(svgStream);
            PNGTranscoder transcoder = new PNGTranscoder();
            outStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outStream);
            transcoder.transcode(input, output);
            byte[] result = outStream.toByteArray();
            this.repositoryService.addModelEditorSourceExtra(model.getId(), result);
        } catch (Exception var11)
        {
            LOGGER.error("Error saving model", var11);
            throw new ActivitiException("Error saving model", var11);
        } finally
        {
            if (outStream != null)
            {
                try
                {
                    outStream.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return R.success();
    }
}
