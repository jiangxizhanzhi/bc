/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.179.10_145
 Source Server Type    : MySQL
 Source Server Version : 80039
 Source Host           : 192.168.179.10:30002
 Source Schema         : CTF

 Target Server Type    : MySQL
 Target Server Version : 80039
 File Encoding         : 65001

 Date: 08/04/2025 20:59:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for p_metrics_indicator
-- ----------------------------
DROP TABLE IF EXISTS `p_metrics_indicator`;
CREATE TABLE `p_metrics_indicator`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `metrics_id` int NULL DEFAULT NULL COMMENT '指标体系id',
  `indicator_id` int NULL DEFAULT NULL COMMENT '指标id',
  `parent_id` int NULL DEFAULT NULL COMMENT '父级关系',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 495 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '指标和体系关联表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of p_metrics_indicator
-- ----------------------------
INSERT INTO `p_metrics_indicator` VALUES (495, 110, 101, 0);
INSERT INTO `p_metrics_indicator` VALUES (496, 110, 104, 101);
INSERT INTO `p_metrics_indicator` VALUES (497, 110, 105, 104);
INSERT INTO `p_metrics_indicator` VALUES (498, 110, 101, 0);
INSERT INTO `p_metrics_indicator` VALUES (499, 110, 107, 101);
INSERT INTO `p_metrics_indicator` VALUES (500, 110, 108, 107);
INSERT INTO `p_metrics_indicator` VALUES (501, 110, 106, 0);
INSERT INTO `p_metrics_indicator` VALUES (502, 110, 109, 106);
INSERT INTO `p_metrics_indicator` VALUES (503, 110, 110, 109);
INSERT INTO `p_metrics_indicator` VALUES (504, 110, 106, 0);
INSERT INTO `p_metrics_indicator` VALUES (505, 110, 112, 106);
INSERT INTO `p_metrics_indicator` VALUES (506, 110, 113, 112);
INSERT INTO `p_metrics_indicator` VALUES (507, 110, 111, 0);
INSERT INTO `p_metrics_indicator` VALUES (508, 110, 114, 111);
INSERT INTO `p_metrics_indicator` VALUES (509, 110, 115, 114);
INSERT INTO `p_metrics_indicator` VALUES (510, 110, 111, 0);
INSERT INTO `p_metrics_indicator` VALUES (511, 110, 117, 111);
INSERT INTO `p_metrics_indicator` VALUES (512, 110, 118, 117);
INSERT INTO `p_metrics_indicator` VALUES (513, 110, 116, 0);
INSERT INTO `p_metrics_indicator` VALUES (514, 110, 120, 116);
INSERT INTO `p_metrics_indicator` VALUES (515, 110, 121, 120);
INSERT INTO `p_metrics_indicator` VALUES (516, 110, 116, 0);
INSERT INTO `p_metrics_indicator` VALUES (517, 110, 124, 116);
INSERT INTO `p_metrics_indicator` VALUES (518, 110, 125, 124);
INSERT INTO `p_metrics_indicator` VALUES (519, 110, 123, 0);
INSERT INTO `p_metrics_indicator` VALUES (520, 110, 127, 123);
INSERT INTO `p_metrics_indicator` VALUES (521, 110, 128, 127);
INSERT INTO `p_metrics_indicator` VALUES (522, 110, 123, 0);
INSERT INTO `p_metrics_indicator` VALUES (523, 110, 132, 123);
INSERT INTO `p_metrics_indicator` VALUES (524, 110, 133, 132);
INSERT INTO `p_metrics_indicator` VALUES (525, 110, 131, 0);
INSERT INTO `p_metrics_indicator` VALUES (526, 110, 134, 131);
INSERT INTO `p_metrics_indicator` VALUES (527, 110, 135, 134);
INSERT INTO `p_metrics_indicator` VALUES (528, 110, 131, 0);
INSERT INTO `p_metrics_indicator` VALUES (529, 110, 136, 131);
INSERT INTO `p_metrics_indicator` VALUES (530, 110, 137, 136);
INSERT INTO `p_metrics_indicator` VALUES (531, 110, 131, 0);
INSERT INTO `p_metrics_indicator` VALUES (532, 110, 138, 131);
INSERT INTO `p_metrics_indicator` VALUES (533, 110, 139, 138);
INSERT INTO `p_metrics_indicator` VALUES (534, 110, 131, 0);
INSERT INTO `p_metrics_indicator` VALUES (535, 110, 140, 131);
INSERT INTO `p_metrics_indicator` VALUES (536, 110, 141, 140);
INSERT INTO `p_metrics_indicator` VALUES (537, 110, 131, 0);
INSERT INTO `p_metrics_indicator` VALUES (538, 110, 142, 131);
INSERT INTO `p_metrics_indicator` VALUES (539, 110, 143, 142);
INSERT INTO `p_metrics_indicator` VALUES (540, 110, 131, 0);
INSERT INTO `p_metrics_indicator` VALUES (541, 110, 145, 131);
INSERT INTO `p_metrics_indicator` VALUES (542, 110, 146, 145);
INSERT INTO `p_metrics_indicator` VALUES (543, 110, 144, 0);
INSERT INTO `p_metrics_indicator` VALUES (544, 110, 149, 144);
INSERT INTO `p_metrics_indicator` VALUES (545, 110, 150, 149);
INSERT INTO `p_metrics_indicator` VALUES (546, 110, 144, 0);
INSERT INTO `p_metrics_indicator` VALUES (547, 110, 153, 144);
INSERT INTO `p_metrics_indicator` VALUES (548, 110, 154, 153);
INSERT INTO `p_metrics_indicator` VALUES (549, 110, 144, 0);
INSERT INTO `p_metrics_indicator` VALUES (550, 110, 155, 144);
INSERT INTO `p_metrics_indicator` VALUES (551, 110, 156, 155);
INSERT INTO `p_metrics_indicator` VALUES (552, 110, 144, 0);
INSERT INTO `p_metrics_indicator` VALUES (553, 110, 157, 144);
INSERT INTO `p_metrics_indicator` VALUES (554, 110, 158, 157);
INSERT INTO `p_metrics_indicator` VALUES (555, 110, 144, 0);
INSERT INTO `p_metrics_indicator` VALUES (556, 110, 160, 144);
INSERT INTO `p_metrics_indicator` VALUES (557, 110, 161, 160);
INSERT INTO `p_metrics_indicator` VALUES (558, 110, 144, 0);
INSERT INTO `p_metrics_indicator` VALUES (559, 110, 162, 144);
INSERT INTO `p_metrics_indicator` VALUES (560, 110, 163, 162);
INSERT INTO `p_metrics_indicator` VALUES (561, 110, 144, 0);
INSERT INTO `p_metrics_indicator` VALUES (562, 110, 165, 144);
INSERT INTO `p_metrics_indicator` VALUES (563, 110, 166, 165);
INSERT INTO `p_metrics_indicator` VALUES (564, 110, 164, 0);
INSERT INTO `p_metrics_indicator` VALUES (565, 110, 167, 164);
INSERT INTO `p_metrics_indicator` VALUES (566, 110, 168, 167);
INSERT INTO `p_metrics_indicator` VALUES (567, 110, 164, 0);
INSERT INTO `p_metrics_indicator` VALUES (568, 110, 169, 164);
INSERT INTO `p_metrics_indicator` VALUES (569, 110, 170, 169);
INSERT INTO `p_metrics_indicator` VALUES (570, 110, 164, 0);
INSERT INTO `p_metrics_indicator` VALUES (571, 110, 171, 164);
INSERT INTO `p_metrics_indicator` VALUES (572, 110, 172, 171);
INSERT INTO `p_metrics_indicator` VALUES (573, 110, 164, 0);
INSERT INTO `p_metrics_indicator` VALUES (574, 110, 173, 164);
INSERT INTO `p_metrics_indicator` VALUES (575, 110, 174, 173);
INSERT INTO `p_metrics_indicator` VALUES (576, 110, 164, 0);
INSERT INTO `p_metrics_indicator` VALUES (577, 110, 176, 164);
INSERT INTO `p_metrics_indicator` VALUES (578, 110, 154, 176);
INSERT INTO `p_metrics_indicator` VALUES (579, 110, 175, 0);
INSERT INTO `p_metrics_indicator` VALUES (580, 110, 177, 175);
INSERT INTO `p_metrics_indicator` VALUES (581, 110, 178, 177);
INSERT INTO `p_metrics_indicator` VALUES (582, 110, 175, 0);
INSERT INTO `p_metrics_indicator` VALUES (583, 110, 179, 175);
INSERT INTO `p_metrics_indicator` VALUES (584, 110, 180, 179);
INSERT INTO `p_metrics_indicator` VALUES (585, 110, 175, 0);
INSERT INTO `p_metrics_indicator` VALUES (586, 110, 182, 175);
INSERT INTO `p_metrics_indicator` VALUES (587, 110, 183, 182);
INSERT INTO `p_metrics_indicator` VALUES (588, 110, 175, 0);
INSERT INTO `p_metrics_indicator` VALUES (589, 110, 185, 175);
INSERT INTO `p_metrics_indicator` VALUES (590, 110, 186, 185);
INSERT INTO `p_metrics_indicator` VALUES (591, 110, 175, 0);
INSERT INTO `p_metrics_indicator` VALUES (592, 110, 188, 175);
INSERT INTO `p_metrics_indicator` VALUES (593, 110, 189, 188);
INSERT INTO `p_metrics_indicator` VALUES (594, 110, 187, 0);
INSERT INTO `p_metrics_indicator` VALUES (595, 110, 190, 187);
INSERT INTO `p_metrics_indicator` VALUES (596, 110, 191, 190);
INSERT INTO `p_metrics_indicator` VALUES (597, 110, 187, 0);
INSERT INTO `p_metrics_indicator` VALUES (598, 110, 194, 187);
INSERT INTO `p_metrics_indicator` VALUES (599, 110, 195, 194);
INSERT INTO `p_metrics_indicator` VALUES (600, 110, 187, 0);
INSERT INTO `p_metrics_indicator` VALUES (601, 110, 197, 187);
INSERT INTO `p_metrics_indicator` VALUES (602, 110, 110, 197);
INSERT INTO `p_metrics_indicator` VALUES (603, 110, 196, 0);
INSERT INTO `p_metrics_indicator` VALUES (604, 110, 198, 196);
INSERT INTO `p_metrics_indicator` VALUES (605, 110, 199, 198);
INSERT INTO `p_metrics_indicator` VALUES (606, 110, 196, 0);
INSERT INTO `p_metrics_indicator` VALUES (607, 110, 176, 196);
INSERT INTO `p_metrics_indicator` VALUES (608, 110, 156, 176);
INSERT INTO `p_metrics_indicator` VALUES (609, 110, 202, 0);
INSERT INTO `p_metrics_indicator` VALUES (610, 110, 203, 202);
INSERT INTO `p_metrics_indicator` VALUES (611, 110, 204, 203);
INSERT INTO `p_metrics_indicator` VALUES (612, 110, 202, 0);
INSERT INTO `p_metrics_indicator` VALUES (613, 110, 205, 202);
INSERT INTO `p_metrics_indicator` VALUES (614, 110, 206, 205);
INSERT INTO `p_metrics_indicator` VALUES (615, 110, 202, 0);
INSERT INTO `p_metrics_indicator` VALUES (616, 110, 207, 202);
INSERT INTO `p_metrics_indicator` VALUES (617, 110, 208, 207);
INSERT INTO `p_metrics_indicator` VALUES (618, 110, 202, 0);
INSERT INTO `p_metrics_indicator` VALUES (619, 110, 209, 202);
INSERT INTO `p_metrics_indicator` VALUES (620, 110, 210, 209);
INSERT INTO `p_metrics_indicator` VALUES (621, 110, 202, 0);
INSERT INTO `p_metrics_indicator` VALUES (622, 110, 211, 202);
INSERT INTO `p_metrics_indicator` VALUES (623, 110, 212, 211);
INSERT INTO `p_metrics_indicator` VALUES (624, 110, 202, 0);
INSERT INTO `p_metrics_indicator` VALUES (625, 110, 213, 202);
INSERT INTO `p_metrics_indicator` VALUES (626, 110, 214, 213);
INSERT INTO `p_metrics_indicator` VALUES (627, 110, 202, 0);
INSERT INTO `p_metrics_indicator` VALUES (628, 110, 216, 202);
INSERT INTO `p_metrics_indicator` VALUES (629, 110, 154, 216);
INSERT INTO `p_metrics_indicator` VALUES (630, 110, 215, 0);
INSERT INTO `p_metrics_indicator` VALUES (631, 110, 217, 215);
INSERT INTO `p_metrics_indicator` VALUES (632, 110, 218, 217);
INSERT INTO `p_metrics_indicator` VALUES (633, 110, 215, 0);
INSERT INTO `p_metrics_indicator` VALUES (634, 110, 219, 215);
INSERT INTO `p_metrics_indicator` VALUES (635, 110, 220, 219);
INSERT INTO `p_metrics_indicator` VALUES (636, 110, 215, 0);
INSERT INTO `p_metrics_indicator` VALUES (637, 110, 221, 215);
INSERT INTO `p_metrics_indicator` VALUES (638, 110, 222, 221);
INSERT INTO `p_metrics_indicator` VALUES (639, 110, 215, 0);
INSERT INTO `p_metrics_indicator` VALUES (640, 110, 224, 215);
INSERT INTO `p_metrics_indicator` VALUES (641, 110, 225, 224);
INSERT INTO `p_metrics_indicator` VALUES (642, 110, 223, 0);
INSERT INTO `p_metrics_indicator` VALUES (643, 110, 227, 223);
INSERT INTO `p_metrics_indicator` VALUES (644, 110, 228, 227);
INSERT INTO `p_metrics_indicator` VALUES (645, 110, 223, 0);
INSERT INTO `p_metrics_indicator` VALUES (646, 110, 229, 223);
INSERT INTO `p_metrics_indicator` VALUES (647, 110, 230, 229);
INSERT INTO `p_metrics_indicator` VALUES (648, 110, 223, 0);
INSERT INTO `p_metrics_indicator` VALUES (649, 110, 232, 223);
INSERT INTO `p_metrics_indicator` VALUES (650, 110, 233, 232);
INSERT INTO `p_metrics_indicator` VALUES (651, 110, 231, 0);
INSERT INTO `p_metrics_indicator` VALUES (652, 110, 234, 231);
INSERT INTO `p_metrics_indicator` VALUES (653, 110, 235, 234);
INSERT INTO `p_metrics_indicator` VALUES (654, 110, 231, 0);
INSERT INTO `p_metrics_indicator` VALUES (655, 110, 236, 231);
INSERT INTO `p_metrics_indicator` VALUES (656, 110, 237, 236);
INSERT INTO `p_metrics_indicator` VALUES (657, 110, 231, 0);
INSERT INTO `p_metrics_indicator` VALUES (658, 110, 238, 231);
INSERT INTO `p_metrics_indicator` VALUES (659, 110, 239, 238);
INSERT INTO `p_metrics_indicator` VALUES (660, 110, 231, 0);
INSERT INTO `p_metrics_indicator` VALUES (661, 110, 240, 231);
INSERT INTO `p_metrics_indicator` VALUES (662, 110, 241, 240);
INSERT INTO `p_metrics_indicator` VALUES (663, 110, 231, 0);
INSERT INTO `p_metrics_indicator` VALUES (664, 110, 242, 231);
INSERT INTO `p_metrics_indicator` VALUES (665, 110, 243, 242);
INSERT INTO `p_metrics_indicator` VALUES (666, 110, 231, 0);
INSERT INTO `p_metrics_indicator` VALUES (667, 110, 245, 231);
INSERT INTO `p_metrics_indicator` VALUES (668, 110, 246, 245);
INSERT INTO `p_metrics_indicator` VALUES (669, 110, 244, 0);
INSERT INTO `p_metrics_indicator` VALUES (670, 110, 247, 244);
INSERT INTO `p_metrics_indicator` VALUES (671, 110, 248, 247);
INSERT INTO `p_metrics_indicator` VALUES (672, 110, 244, 0);
INSERT INTO `p_metrics_indicator` VALUES (673, 110, 249, 244);
INSERT INTO `p_metrics_indicator` VALUES (674, 110, 250, 249);
INSERT INTO `p_metrics_indicator` VALUES (675, 110, 244, 0);
INSERT INTO `p_metrics_indicator` VALUES (676, 110, 251, 244);
INSERT INTO `p_metrics_indicator` VALUES (677, 110, 252, 251);
INSERT INTO `p_metrics_indicator` VALUES (678, 110, 244, 0);
INSERT INTO `p_metrics_indicator` VALUES (679, 110, 253, 244);
INSERT INTO `p_metrics_indicator` VALUES (680, 110, 254, 253);
INSERT INTO `p_metrics_indicator` VALUES (681, 110, 244, 0);
INSERT INTO `p_metrics_indicator` VALUES (682, 110, 256, 244);
INSERT INTO `p_metrics_indicator` VALUES (683, 110, 257, 256);
INSERT INTO `p_metrics_indicator` VALUES (684, 110, 244, 0);
INSERT INTO `p_metrics_indicator` VALUES (685, 110, 259, 244);
INSERT INTO `p_metrics_indicator` VALUES (686, 110, 237, 259);
INSERT INTO `p_metrics_indicator` VALUES (687, 110, 258, 0);
INSERT INTO `p_metrics_indicator` VALUES (688, 110, 262, 258);
INSERT INTO `p_metrics_indicator` VALUES (689, 110, 263, 262);
INSERT INTO `p_metrics_indicator` VALUES (690, 110, 258, 0);
INSERT INTO `p_metrics_indicator` VALUES (691, 110, 264, 258);
INSERT INTO `p_metrics_indicator` VALUES (692, 110, 265, 264);
INSERT INTO `p_metrics_indicator` VALUES (693, 110, 258, 0);
INSERT INTO `p_metrics_indicator` VALUES (694, 110, 266, 258);
INSERT INTO `p_metrics_indicator` VALUES (695, 110, 265, 266);
INSERT INTO `p_metrics_indicator` VALUES (696, 111, 272, 0);
INSERT INTO `p_metrics_indicator` VALUES (697, 111, 276, 272);
INSERT INTO `p_metrics_indicator` VALUES (698, 111, 277, 276);
INSERT INTO `p_metrics_indicator` VALUES (699, 111, 275, 0);
INSERT INTO `p_metrics_indicator` VALUES (700, 111, 279, 275);
INSERT INTO `p_metrics_indicator` VALUES (701, 111, 280, 279);
INSERT INTO `p_metrics_indicator` VALUES (702, 111, 275, 0);
INSERT INTO `p_metrics_indicator` VALUES (703, 111, 281, 275);
INSERT INTO `p_metrics_indicator` VALUES (704, 111, 282, 281);
INSERT INTO `p_metrics_indicator` VALUES (705, 111, 275, 0);
INSERT INTO `p_metrics_indicator` VALUES (706, 111, 284, 275);
INSERT INTO `p_metrics_indicator` VALUES (707, 111, 285, 284);
INSERT INTO `p_metrics_indicator` VALUES (708, 111, 283, 0);
INSERT INTO `p_metrics_indicator` VALUES (709, 111, 286, 283);
INSERT INTO `p_metrics_indicator` VALUES (710, 111, 287, 286);
INSERT INTO `p_metrics_indicator` VALUES (711, 111, 283, 0);
INSERT INTO `p_metrics_indicator` VALUES (712, 111, 288, 283);
INSERT INTO `p_metrics_indicator` VALUES (713, 111, 289, 288);
INSERT INTO `p_metrics_indicator` VALUES (714, 111, 290, 0);
INSERT INTO `p_metrics_indicator` VALUES (715, 111, 292, 290);
INSERT INTO `p_metrics_indicator` VALUES (716, 111, 293, 292);
INSERT INTO `p_metrics_indicator` VALUES (717, 111, 290, 0);
INSERT INTO `p_metrics_indicator` VALUES (718, 111, 295, 290);
INSERT INTO `p_metrics_indicator` VALUES (719, 111, 296, 295);
INSERT INTO `p_metrics_indicator` VALUES (720, 111, 298, 0);
INSERT INTO `p_metrics_indicator` VALUES (721, 111, 300, 298);
INSERT INTO `p_metrics_indicator` VALUES (722, 111, 301, 300);
INSERT INTO `p_metrics_indicator` VALUES (723, 111, 298, 0);
INSERT INTO `p_metrics_indicator` VALUES (724, 111, 303, 298);
INSERT INTO `p_metrics_indicator` VALUES (725, 111, 304, 303);
INSERT INTO `p_metrics_indicator` VALUES (726, 111, 106, 0);
INSERT INTO `p_metrics_indicator` VALUES (727, 111, 306, 106);
INSERT INTO `p_metrics_indicator` VALUES (728, 111, 307, 306);
INSERT INTO `p_metrics_indicator` VALUES (729, 111, 305, 0);
INSERT INTO `p_metrics_indicator` VALUES (730, 111, 308, 305);
INSERT INTO `p_metrics_indicator` VALUES (731, 111, 309, 308);
INSERT INTO `p_metrics_indicator` VALUES (732, 111, 310, 0);
INSERT INTO `p_metrics_indicator` VALUES (733, 111, 312, 310);
INSERT INTO `p_metrics_indicator` VALUES (734, 111, 313, 312);
INSERT INTO `p_metrics_indicator` VALUES (735, 111, 310, 0);
INSERT INTO `p_metrics_indicator` VALUES (736, 111, 315, 310);
INSERT INTO `p_metrics_indicator` VALUES (737, 111, 316, 315);
INSERT INTO `p_metrics_indicator` VALUES (738, 111, 314, 0);
INSERT INTO `p_metrics_indicator` VALUES (739, 111, 317, 314);
INSERT INTO `p_metrics_indicator` VALUES (740, 111, 318, 317);
INSERT INTO `p_metrics_indicator` VALUES (741, 111, 314, 0);
INSERT INTO `p_metrics_indicator` VALUES (742, 111, 320, 314);
INSERT INTO `p_metrics_indicator` VALUES (743, 111, 321, 320);
INSERT INTO `p_metrics_indicator` VALUES (744, 111, 323, 0);
INSERT INTO `p_metrics_indicator` VALUES (745, 111, 325, 323);
INSERT INTO `p_metrics_indicator` VALUES (746, 111, 326, 325);

SET FOREIGN_KEY_CHECKS = 1;
