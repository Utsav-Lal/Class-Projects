class vert():
	verts = []
	def __init__(self, edges):
		self.edges = edges
		vert.verts.append(self)
	def connect(self):
		self.edges = vert.verts.copy()

class edg():
	edges = []
	def __init__(self, verts):
		self.verts = verts
		edg.edges.append(self)

for i in vert.verts:
	connect(i)
for x in range(len(vert.verts)):
	

