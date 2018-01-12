CREATE KEYSPACE cass_platform_data WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  AND durable_writes = true;

CREATE TABLE cass_platform_data.location_md (
    parent_id int,
    loc_type_id int,
    verification_status int,
    loc_id int,
    area text,
    artificial_wc text,
    canal_info text,
    crop_info text,
    geological_info text,
    gw_dependency text,
    gw_specific_yield double,
    infiltration_info text,
    loc_association text,
    loc_name text,
    population text,
    processed_status int,
    resource_distribution text,
    rf_infiltration_rate double,
    soil_type text,
    storage_coefficient double,
    transmissivity double,
    water_bodies text,
    PRIMARY KEY ((parent_id, loc_type_id, verification_status), loc_id)
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


CREATE TABLE cass_platform_data.gec_meta_data (
    parent_id int PRIMARY KEY,
    canal_infiltration text,
    dependency_factor text,
    geological_info text,
    gw_infiltration text,
    infiltration_info text
) WITH bloom_filter_fp_chance = 0.01
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

