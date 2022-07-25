function drawMind(elementId, mindData){

    var mind = {
        meta: {
          name: 'demo',
        },
        format: 'node_array',
        data: mindData,
      };
      var options = {
        container: elementId,
        theme: '#1C2026',
        editable: false,
        mode: 'side',
        view: {
          // engine: 'svg',
          line_width: 1,
          line_color: '#626569',
        },
      };
      var jm = new jsMind(options);
      jm.show(mind);
}