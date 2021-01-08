<template>
  <div class="nodeSetting">

    <div class="nodeImg">
      <div style="text-align: center">
        <img :src="resourceImgUrl"/>
      </div>
    </div>

    <div class="typeTip">
      <div>
        <img src="@/assets/start.png">
      </div>
      <div>
        <img src="@/assets/userTask.png">
      </div>
      <div>
        <img src="@/assets/end.png">
      </div>
      <div>
        <img src="@/assets/ExclusiveGateway.png">
      </div>
      <div>
        <img src="@/assets/ParallelGateway.png">
      </div>
    </div>

    <div class="event">
      <template v-for="(node, index) in nodes" v-if="node.nodeType!=nodeType.SEQ_FLOW">

        <el-popover
          placement="top-start"
          :width="node.nodeType==nodeType.ParallelGateway?'500':'420'"
          trigger="hover"
        >
          <div>
            <!--            用户节点-->
            <p>节点名称：{{node.name}}</p>
            <p>节点编号：<span class="flowId">{{node.id}}</span></p>
            <p v-if="node.nodeType==nodeType.USER_TASK">审批人： {{node.flowElement[0].assignee}}</p>
            <el-button v-if="node.nodeType==nodeType.USER_TASK">设置审批人</el-button>

            <!--       排斥网关 -->
            <div v-if="node.nodeType==nodeType.ExclusiveGateway">
              <template v-for="(flow, index) in node.flowElement[0].outgoingFlows">

                <div style="border:1px solid #ebebeb;padding: 10px">
                  <span>分之{{index+1}}：--{{flow.name}}--></span>
                  <p>编号：<span class="flowId">{{flow.id}}</span></p>
                  <p>走向节点：<span class="targetRef">{{flow.targetRef}}</span></p>
                  <p>分之走向条件：{{flow.conditionExpression}}</p>
                </div>

              </template>
            </div>
          </div>

          <div slot="reference"
               :class="{'event-item':true,'startEventClass':node.nodeType==nodeType.START,'cursor':node.nodeType==nodeType.USER_TASK,'border_green':node.nodeType==nodeType.USER_TASK}">
            <img v-if="node.nodeType==nodeType.START" src="@/assets/start.png">
            <img v-else-if="node.nodeType==nodeType.END" src="@/assets/end.png">
            <img v-else-if="node.nodeType==nodeType.ExclusiveGateway" src="@/assets/ExclusiveGateway.png">
            <img v-else-if="node.nodeType==nodeType.ParallelGateway" src="@/assets/ParallelGateway.png">
            <img v-else-if="node.nodeType==nodeType.USER_TASK" src="@/assets/userTask.png">
            <div :class="{'name':true}">
              <strong>{{node.name}}</strong>

            </div>
          </div>
        </el-popover>
      </template>
    </div>


  </div>
</template>
<script>
  export default {
    data() {
      return {

        nodeType: {
          START: 'START_EVENT',
          END: 'END_EVENT',
          SEQ_FLOW: 'SEQ_FLOW',
          ParallelGateway: 'ParallelGateway',
          ExclusiveGateway: 'ExclusiveGateway',
          USER_TASK: 'USER_TASK'
        },
        nodes: [],
        resourceImgUrl: "",


        URL: {
          getNodes: "/activity/processDef/getProcDefNodes",
          checkImg: this.$serverContextPath + "/activity/processDef/checkImgByProcDefId/{procDefId}",
        },
      };
    },
    watch: {
      $route: {
        handler: function(val, oldVal){
          let procDefId = this.$route.query.id;
          this.checkImg(procDefId)
          this.getNodes(procDefId);
        },
        // 深度观察监听
        deep: true
      },
    },
    mounted(){

    },
    created() {
      let procDefId = this.$route.query.id;
      this.checkImg(procDefId)
      this.getNodes(procDefId);
    },
    methods: {
      getNodes(procDefId) {
        this.$get(this.URL.getNodes, {procDefId: procDefId}).then((res) => {
          this.nodes = res.data
        });
      },
      checkImg(procDefId) {
        this.resourceImgUrl = this.URL.checkImg.replace(
          "{procDefId}",
          procDefId
        );
      },
    },
  };
</script>
<style scoped>
  .nodeSetting {
    width: 100%;
  }

  .event, .typeTip {
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .typeTip img {
    background: none;
    height: 30px;
    margin: 10px 20px;
  }


  .event-item {
    width: 120px;
    height: 70px;
    display: flex;
    flex-direction: column;
    border: 1px solid #d3dce6;
    border-radius: 10px;
    margin: 20px 30px;
    align-items: center;
  }

  .event-item img {
    margin-bottom: 10px;
    height: 25px;
  }

  .event-item.cursor {
    cursor: pointer;
  }
  .flowId{
    font-size: 15px;
    font-weight: 600;
  }
  .targetRef{
    color: red;
  }

  .event-item .name {
    font-size: 17px;
  }

  .event-item.border_green {
    border: 1px solid #20b2aa;
  }
</style>
