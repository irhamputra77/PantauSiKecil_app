package com.pantausikecil.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.pantausikecil.model.PosyanduSchedule

@Composable
fun LatestScheduleSection(
    schedule: PosyanduSchedule?,
    isTablet: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (isTablet) {
                    figmaDp(28f)
                } else {
                    figmaDp(16f)
                },
                shape = RoundedCornerShape(
                    if (isTablet) {
                        figmaDp(13f)
                    } else {
                        figmaDp(10f)
                    }
                ),
                ambientColor = Color.Black.copy(alpha = 0.2f),
                spotColor = Color.Black.copy(alpha = 0.2f)
            )
            .clip(
                RoundedCornerShape(
                    if (isTablet) {
                        figmaDp(13f)
                    } else {
                        figmaDp(10f)
                    }
                )
            )
            .background(Color.White)
            .padding(
                if (isTablet) {
                    figmaDp(32f)
                } else {
                    figmaDp(18f)
                }
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                if (isTablet) {
                    figmaDp(38f)
                } else {
                    figmaDp(18f)
                }
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.width(
                    if (isTablet) {
                        figmaDp(330f)
                    } else {
                        figmaDp(160f)
                    }
                )
            ) {
                Text(
                    text = "Jadwal\nTerbaru",
                    color = Color.Black,
                    fontSize = if (isTablet) {
                        figmaSp(40f)
                    } else {
                        figmaSp(23f)
                    },
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = if (isTablet) {
                        figmaSp(46f)
                    } else {
                        figmaSp(28f)
                    }
                )

                Spacer(
                    modifier = Modifier.height(
                        if (isTablet) {
                            figmaDp(55f)
                        } else {
                            figmaDp(26f)
                        }
                    )
                )

                DashboardGradientButton(
                    text = "Kirim WhatsApp",
                    height = if (isTablet) {
                        figmaDp(75f)
                    } else {
                        figmaDp(42f)
                    },
                    radius = if (isTablet) {
                        figmaDp(10f)
                    } else {
                        figmaDp(8f)
                    },
                    fontSize = if (isTablet) {
                        figmaSp(24f)
                    } else {
                        figmaSp(13f)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        // Nanti bisa disambungkan ke API trigger jadwal.
                    }
                )

                Spacer(
                    modifier = Modifier.height(
                        if (isTablet) {
                            figmaDp(35f)
                        } else {
                            figmaDp(18f)
                        }
                    )
                )

                DashboardDarkButton(
                    text = "Edit Pesan",
                    height = if (isTablet) {
                        figmaDp(75f)
                    } else {
                        figmaDp(42f)
                    },
                    radius = if (isTablet) {
                        figmaDp(10f)
                    } else {
                        figmaDp(8f)
                    },
                    fontSize = if (isTablet) {
                        figmaSp(24f)
                    } else {
                        figmaSp(13f)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        // Nanti bisa disambungkan ke dialog edit pesan.
                    }
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .shadow(
                        elevation = if (isTablet) {
                            figmaDp(22f)
                        } else {
                            figmaDp(12f)
                        },
                        shape = RoundedCornerShape(
                            if (isTablet) {
                                figmaDp(13f)
                            } else {
                                figmaDp(10f)
                            }
                        )
                    )
                    .clip(
                        RoundedCornerShape(
                            if (isTablet) {
                                figmaDp(13f)
                            } else {
                                figmaDp(10f)
                            }
                        )
                    )
                    .background(Color.White)
                    .padding(
                        if (isTablet) {
                            figmaDp(26f)
                        } else {
                            figmaDp(16f)
                        }
                    )
            ) {
                if (schedule == null) {
                    EmptyLatestScheduleContent(
                        isTablet = isTablet
                    )
                } else {
                    LatestScheduleContent(
                        schedule = schedule,
                        isTablet = isTablet
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyLatestScheduleContent(
    isTablet: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(
                if (isTablet) {
                    figmaDp(180f)
                } else {
                    figmaDp(110f)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Tidak ada data",
            color = Color(0xFF6D6D6D),
            fontSize = if (isTablet) {
                figmaSp(28f)
            } else {
                figmaSp(16f)
            },
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun LatestScheduleContent(
    schedule: PosyanduSchedule,
    isTablet: Boolean
) {
    Column {
        Text(
            text = schedule.title,
            modifier = Modifier.fillMaxWidth(),
            color = Color.Black,
            fontSize = if (isTablet) {
                figmaSp(32f)
            } else {
                figmaSp(18f)
            },
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(
                if (isTablet) {
                    figmaDp(20f)
                } else {
                    figmaDp(10f)
                }
            )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(figmaDp(1.5f))
                .background(Color(0xFF5E5E5E))
        )

        Spacer(
            modifier = Modifier.height(
                if (isTablet) {
                    figmaDp(22f)
                } else {
                    figmaDp(12f)
                }
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LatestScheduleMeta(
                iconType = LatestScheduleIconType.Date,
                text = schedule.date,
                isTablet = isTablet
            )

            LatestScheduleMeta(
                iconType = LatestScheduleIconType.Time,
                text = schedule.time,
                isTablet = isTablet
            )
        }

        Spacer(
            modifier = Modifier.height(
                if (isTablet) {
                    figmaDp(55f)
                } else {
                    figmaDp(28f)
                }
            )
        )

        Text(
            text = schedule.activity,
            color = Color.Black,
            fontSize = if (isTablet) {
                figmaSp(23f)
            } else {
                figmaSp(14f)
            },
            fontWeight = FontWeight.Normal,
            lineHeight = if (isTablet) {
                figmaSp(34f)
            } else {
                figmaSp(21f)
            }
        )
    }
}

private enum class LatestScheduleIconType {
    Date,
    Time
}

@Composable
private fun LatestScheduleMeta(
    iconType: LatestScheduleIconType,
    text: String,
    isTablet: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = when (iconType) {
                LatestScheduleIconType.Date -> Icons.Filled.Today
                LatestScheduleIconType.Time -> Icons.Filled.Schedule
            },
            contentDescription = null,
            tint = Color(0xFF6D6D6D),
            modifier = Modifier.size(
                if (isTablet) {
                    figmaDp(28f)
                } else {
                    figmaDp(15f)
                }
            )
        )

        Spacer(
            modifier = Modifier.width(
                if (isTablet) {
                    figmaDp(8f)
                } else {
                    figmaDp(5f)
                }
            )
        )

        Text(
            text = text,
            color = Color(0xFF6D6D6D),
            fontSize = if (isTablet) {
                figmaSp(24f)
            } else {
                figmaSp(13f)
            }
        )
    }
}