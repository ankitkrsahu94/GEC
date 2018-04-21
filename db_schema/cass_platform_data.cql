CREATE KEYSPACE cass_platform_data WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  AND durable_writes = true;

CREATE TABLE cass_platform_data.location_md (
    parent_id int,
    loc_type_id int,
    verific_status int,
    assessment_year text,
    loc_id int,
    aquifer_info text,
    area text,
    artificial_wc text,
    base_flow text,
    canal_discharge text,
    canal_info text,
    crop_info text,
    geo_info text,
    gw_data text,
    old_gw_data text,
    gw_dependency text,
    gw_specific_yield double,
    ind_consump text,
    infil_info text,
    irri_consump text,
    loc_assoc text,
    loc_name text,
    other_rechargeable_areas text,
    population text,
    processed_status int,
    resource_dist text,
    rf_data text,
    old_rf_data text,
    rf_infil_rate double,
    soil_type text,
    water_bodies text,
    PRIMARY KEY ((parent_id, loc_type_id, verific_status, assessment_year), loc_id)
) WITH CLUSTERING ORDER BY (loc_id ASC)
    AND bloom_filter_fp_chance = 0.01
    AND caching = {'keys': 'ALL', 'rows_per_partition': 'NONE'}
    AND comment = ''
    AND compaction = {'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy', 'max_threshold': '32', 'min_threshold': '4'}
    AND compression = {'chunk_length_in_kb': '64', 'class': 'org.apache.cassandra.io.compress.LZ4Compressor'}
    AND crc_check_chance = 1.0
    AND dclocal_read_repair_chance = 0.1
    AND default_time_to_live = 0
    AND gc_grace_seconds = 864000
    AND max_index_interval = 2048
    AND memtable_flush_period_in_ms = 0
    AND min_index_interval = 128
    AND read_repair_chance = 0.0
    AND speculative_retry = '99PERCENTILE';

